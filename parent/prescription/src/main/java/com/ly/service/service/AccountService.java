package com.ly.service.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.SellerAccountRecord;
import com.ly.service.entity.StoreAccount;
import com.ly.service.entity.StoreAccountRecord;
import com.ly.service.mapper.*;
import com.ly.service.utils.DateUtils;
import com.ly.service.utils.RedissonUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class AccountService {

	@Autowired
	RedissonUtil redissonUtil;
	
	@Autowired
	SellerAccountMapper sellerAccountMapper;
	@Autowired
	SellerAccountRecordMapper sellerRecordMapper;
	@Autowired
	StoreAccountMapper storeAccountMapper;
	@Autowired
	StoreAccountRecordMapper storeRecordMapper;
	
	private SellerAccount getSellerAccount(int sellerid) {
		Example ex = new Example(SellerAccount.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid);
		SellerAccount account = sellerAccountMapper.selectOneByExample(ex);
		if(null == account){
			account = new SellerAccount();
			account.setSellerid(sellerid);
			account.setBalance(0);
			sellerAccountMapper.insertUseGeneratedKeys(account);
		}
		return account;
	}
	
	public int getSellerBalance(int sellerid) {
		Example ex = new Example(SellerAccount.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid);
		SellerAccount account = sellerAccountMapper.selectOneByExample(ex);
		if(account==null){
			return 0;
		}
		return account.getBalance();
	}

	@Transactional
	public void settleSalesRecords(List<SalesRecord> salesRecords) {
		for(SalesRecord salesRecord: salesRecords){
			int sellerid = salesRecord.getSellerid();
			int totalSellerfee = salesRecord.getTotalsellerfee();
			addSellerAccount(sellerid, totalSellerfee, "奖励处方:"+salesRecord.getPrescriptionid()+",领药记录:"+salesRecord.getId()+",售卖"+salesRecord.getDrugname()+"*"+salesRecord.getNum());
			
			int storeid = salesRecord.getStoreid();
			int totalSettleamount = salesRecord.getTotalsettlementprice();
			reduceStoreAccount(storeid, totalSettleamount, "结算处方:"+salesRecord.getPrescriptionid()+",领药记录:"+salesRecord.getId()+",售卖"+salesRecord.getDrugname()+"*"+salesRecord.getNum());
		}
		return;
	}

	@Transactional
	public StoreAccount reduceStoreAccount(int storeid, int amount, String msg) {
		boolean isLock = redissonUtil.tryLock("STORE_ACCOUNT_"+storeid, TimeUnit.MILLISECONDS, 1000, 1500);
		if(isLock){
			StoreAccount account = getStoreAccount(storeid);
			if(account.getBalance()>=amount){
				account.setBalance(account.getBalance()-amount);
				storeAccountMapper.updateByPrimaryKey(account);
				
				StoreAccountRecord record = new StoreAccountRecord();
				record.setAmount(amount);
				record.setType(StoreAccountRecord.TYPE_PAYOUT);
				record.setCreatetime(new Date());
				record.setStoreid(storeid);
				record.setMsg(msg);
				storeRecordMapper.insert(record);
				redissonUtil.unlock("STORE_ACCOUNT_"+storeid);
				return account;
			}else{
				redissonUtil.unlock("STORE_ACCOUNT_"+storeid);
				throw new HandleException(ErrorCode.BALANCE_ERROR, "余额不足");
			}
			
		}else{
			throw new HandleException(ErrorCode.SYSTEM_ERROR, "系统异常");
		}
	}

	private StoreAccount getStoreAccount(int storeid) {
		Example ex = new Example(StoreAccount.class);
		ex.createCriteria().andEqualTo("storeid", storeid);
		StoreAccount account = storeAccountMapper.selectOneByExample(ex);
		if(null == account){
			account = new StoreAccount();
			account.setStoreid(storeid);
			account.setBalance(0);
			storeAccountMapper.insertUseGeneratedKeys(account);
		}
		return account;
	}

	@Transactional
	public StoreAccount addStoreAccount(int storeid, int amount, String msg) {
		boolean isLock = redissonUtil.tryLock("STORE_ACCOUNT_"+storeid, TimeUnit.MILLISECONDS, 1000, 1500);
		if(isLock){
			StoreAccount account = getStoreAccount(storeid);
			account.setBalance(account.getBalance()+amount);
			storeAccountMapper.updateByPrimaryKey(account);
			
			StoreAccountRecord record = new StoreAccountRecord();
			record.setAmount(amount);
			record.setType(StoreAccountRecord.TYPE_INCOME);
			record.setCreatetime(new Date());
			record.setStoreid(storeid);
			record.setMsg(msg);
			storeRecordMapper.insert(record);
			redissonUtil.unlock("STORE_ACCOUNT_"+storeid);
			return account;
		}else{
			throw new HandleException(ErrorCode.SYSTEM_ERROR, "系统异常");
		}
	}

	@Transactional
	public SellerAccount reduceSellerAccount(int sellerid, int amount, String msg) {
		boolean isLock = redissonUtil.tryLock("SELLER_ACCOUNT_"+sellerid, TimeUnit.MILLISECONDS, 1000, 1500);
		if(isLock){
			SellerAccount account = getSellerAccount(sellerid);
			if(account.getBalance()>=amount){
				account.setBalance(account.getBalance()-amount);
				sellerAccountMapper.updateByPrimaryKey(account);
				
				SellerAccountRecord record = new SellerAccountRecord();
				record.setAmount(amount);
				record.setType(SellerAccountRecord.TYPE_PAYOUT);
				record.setCreatetime(new Date());
				record.setSellerid(sellerid);
				record.setMsg(msg);
				sellerRecordMapper.insert(record);
				redissonUtil.unlock("SELLER_ACCOUNT_"+sellerid);
				return account;
			}else{
				redissonUtil.unlock("SELLER_ACCOUNT_"+sellerid);
				throw new HandleException(ErrorCode.BALANCE_ERROR, "余额不足");
			}
		}else{
			throw new HandleException(ErrorCode.SYSTEM_ERROR, "系统异常");
		}
	}

	@Transactional
	public SellerAccount addSellerAccount(int sellerid, int amount, String msg) {
		boolean isLock = redissonUtil.tryLock("SELLER_ACCOUNT_"+sellerid, TimeUnit.MILLISECONDS, 1000, 1500);
		if(isLock){
			SellerAccount account = getSellerAccount(sellerid);
			account.setBalance(account.getBalance()+amount);
			sellerAccountMapper.updateByPrimaryKey(account);
			
			SellerAccountRecord record = new SellerAccountRecord();
			record.setAmount(amount);
			record.setType(SellerAccountRecord.TYPE_INCOME);
			record.setCreatetime(new Date());
			record.setSellerid(sellerid);
			record.setMsg(msg);
			sellerRecordMapper.insert(record);
			redissonUtil.unlock("SELLER_ACCOUNT_"+sellerid);
			return account;
		}else{
			throw new HandleException(-1, "系统异常");
		}
	}

	public int getStoreBalance(int id) {
		Example ex = new Example(StoreAccount.class);
		ex.createCriteria().andEqualTo("storeid", id);
		StoreAccount account = storeAccountMapper.selectOneByExample(ex);
		if(account==null){
			return 0;
		}
		return account.getBalance();
	}

	public List<SellerAccountRecord> getSellerAccountRecord(int sellerid, String startDate, String endDate, int pageIndex, int pageSize) {
		if(startDate==null || startDate.isEmpty()){
			startDate = "1970-1-1";
		}else{
			startDate = DateUtils.UTCStringtODefaultString(startDate);
		}
		if(endDate == null || endDate.isEmpty()){
			endDate = "2099-12-31";
		}else{
			endDate = DateUtils.UTCStringtODefaultString(endDate);
		}
		Example ex = new Example(SellerAccountRecord.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid).andBetween("createtime", startDate, endDate);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return sellerRecordMapper.selectByExampleAndRowBounds(ex, rowBounds);
	}

	public List<StoreAccountRecord> getStoreAccountRecord(int storeid,String startDate, String endDate, int pageIndex, int pageSize) {
		if(startDate==null || startDate.isEmpty()){
			startDate = "1970-1-1";
		}
		if(endDate == null || endDate.isEmpty()){
			endDate = "2099-12-31";
		}
		Example ex = new Example(StoreAccountRecord.class);
		ex.createCriteria().andEqualTo("storeid", storeid).andBetween("createtime", startDate, endDate);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return storeRecordMapper.selectByExampleAndRowBounds(ex, rowBounds);
	}

	public List<SellerAccount> getAllSellerAccount(int pageIndex, int pageSize) {
		Example ex = new Example(SellerAccount.class);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return sellerAccountMapper.selectByExampleAndRowBounds(ex, rowBounds);
	}

	public List<StoreAccount> getAllStoreAccount(int pageIndex, int pageSize) {
		Example ex = new Example(StoreAccount.class);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return storeAccountMapper.selectByExampleAndRowBounds(ex, rowBounds);
	}

	public SellerAccount updateSellerBalance(int sellerid, int type, int amount) {
		if(amount <= 0){
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
		if(type == 1){
			return addSellerAccount(sellerid, amount, "系统调账");
		}else if(type == 2){
			return reduceSellerAccount(sellerid, amount, "系统调账");
		}else{
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
	}

	public StoreAccount updateStoreBalance(int storeid, int type, int amount) {
		if(amount <= 0){
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
		if(type == 1){
			return addStoreAccount(storeid, amount, "系统调账");
		}else if(type == 2){
			return reduceStoreAccount(storeid, amount, "系统调账");
		}else{
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
	}
	
}
