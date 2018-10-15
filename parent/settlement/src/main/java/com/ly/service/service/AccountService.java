package com.ly.service.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.HandleException;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.SellerAccount;
import com.ly.service.entity.SellerAccountRecord;
import com.ly.service.entity.StoreAccount;
import com.ly.service.entity.StoreAccountRecord;
import com.ly.service.mapper.*;
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
		ex.createCriteria().andEqualTo("storeid", sellerid);
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
			int sellfee = salesRecord.getSellerfee()*salesRecord.getNum();
			addSellerAccount(sellerid, sellfee, "处方:"+salesRecord.getPrescriptionid()+",领药记录:"+salesRecord.getId()+"推广费奖励");
			
			int storeid = salesRecord.getStoreid();
			int settleamount = salesRecord.getSettlementprice()*salesRecord.getNum();
			reduceStoreAccount(storeid, settleamount, "结算处方:"+salesRecord.getPrescriptionid()+",领药记录:"+salesRecord.getId()+"领取"+salesRecord.getDrugname()+"*"+salesRecord.getNum());
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
				record.setCreatedate(new Date());
				record.setStoreid(storeid);
				record.setMsg(msg);
				storeRecordMapper.insert(record);
				redissonUtil.unlock("STORE_ACCOUNT_"+storeid);
				return account;
			}else{
				redissonUtil.unlock("STORE_ACCOUNT_"+storeid);
				throw new HandleException(-1, "余额不足");
			}
			
		}else{
			throw new HandleException(-1, "系统异常");
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
			record.setCreatedate(new Date());
			record.setStoreid(storeid);
			record.setMsg(msg);
			storeRecordMapper.insert(record);
			redissonUtil.unlock("STORE_ACCOUNT_"+storeid);
			return account;
		}else{
			throw new HandleException(-1, "系统异常");
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
				record.setCreatedate(new Date());
				record.setSellerid(sellerid);
				record.setMsg(msg);
				sellerRecordMapper.insert(record);
				redissonUtil.unlock("SELLER_ACCOUNT_"+sellerid);
				return account;
			}else{
				redissonUtil.unlock("SELLER_ACCOUNT_"+sellerid);
				throw new HandleException(-1, "余额不足");
			}
		}else{
			throw new HandleException(-1, "系统异常");
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
			record.setCreatedate(new Date());
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

	public List<SellerAccountRecord> getSellerAccountRecord(int sellerid, int pageIndex, int pageSize) {
		Example ex = new Example(SellerAccountRecord.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return sellerRecordMapper.selectByExampleAndRowBounds(ex, rowBounds);
	}

	public List<StoreAccountRecord> getStoreAccountRecord(int storeid, int pageIndex, int pageSize) {
		Example ex = new Example(StoreAccountRecord.class);
		ex.createCriteria().andEqualTo("storeid", storeid);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		return storeRecordMapper.selectByExampleAndRowBounds(ex, rowBounds);
	}

	
}
