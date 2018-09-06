package com.ly.service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.AccountClient;
import com.ly.service.feign.client.StoreClient;
import com.ly.service.mapper.SalesRecordMapper;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.RedissonUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class SalesRecordService {

	@Autowired
	SalesRecordMapper recordMapper;
	@Autowired
	StoreClient storeClient;
	@Autowired
	AccountClient accountClient;
	@Autowired
	RedissonUtil redissonUtil;
	
	public List<SalesRecord> getRecordByStore(Integer storeid, int pageIndex, int maxSize){
		Example ex = new Example(SalesRecord.class);
		ex.createCriteria().andEqualTo("storeid", storeid);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds(pageIndex*maxSize, maxSize);
		List<SalesRecord> list = recordMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return list;
	}
	
	@Transactional
	public void createByStore(Integer storeid, Order order){
		
		String transactionInfo =  order.getInfo();
		List<TransactionDrug> transactionList = null;
		try{
			transactionList = JSONUtils.getObjectListByJson(transactionInfo, TransactionDrug.class);
		}catch (Exception e) {
			//FIXME:此处不应该出现问题
			e.printStackTrace();
		}
		List<SalesRecord> recordList = new ArrayList<SalesRecord>();
		for(TransactionDrug transdrug : transactionList){
			SalesRecord record = new SalesRecord();
			
			record.setCreatetime(new Date());
			
			record.setDoctorname(transdrug.getDoctorname());
			record.setDoctorid(transdrug.getDoctorid());
			
			record.setDrugid(transdrug.getDrugid());
			record.setDrugname(transdrug.getDrugname());
			
			record.setSellerid(transdrug.getSellerid());
			record.setSellername(transdrug.getSellername());
			record.setSellerfee(transdrug.getSellerfee());
			
			record.setHospitalid(transdrug.getHospitalid());
			record.setHospitalname(transdrug.getHospitalname());
			
			record.setOrderid(order.getId());
			record.setPrescriptionid(transdrug.getPrescriptionid());
			
			StoreDrug storeDrug = storeClient.getDrugByStore(storeid, transdrug.getDrugid());
			
			record.setSettlementprice(storeDrug.getSettlementprice());
			record.setStoreid(storeid);
			record.setNum(transdrug.getNum());
			
			recordList.add(record);
		}

		recordMapper.insertList(recordList);
		
		accountClient.settleSalesRecords(recordList);//结算交易
	}

	public void createByOnlinePay(Order order) {
		String transactionInfo =  order.getInfo();
		
		List<TransactionDrug> transactionList = null;
		try {
			transactionList = JSONUtils.getObjectListByJson(transactionInfo, TransactionDrug.class);
		} catch (Exception e) {
			// FIXME: 此处不应该出现问题
			e.printStackTrace();
		} 
		
		List<SalesRecord> recordList = new ArrayList<SalesRecord>();
		for(TransactionDrug transdrug : transactionList){
			SalesRecord record = new SalesRecord();
			
			record.setCreatetime(new Date());
			
			record.setDoctorname(transdrug.getDoctorname());
			record.setDoctorid(transdrug.getDoctorid());
			
			record.setDrugid(transdrug.getDrugid());
			record.setDrugname(transdrug.getDrugname());
			
			record.setSellerid(transdrug.getSellerid());
			record.setSellername(transdrug.getSellername());
			record.setSellerfee(transdrug.getSellerfee());
			
			record.setHospitalid(transdrug.getHospitalid());
			record.setHospitalname(transdrug.getHospitalname());
			
			record.setOrderid(order.getId());
			record.setPrescriptionid(transdrug.getPrescriptionid());
			
			record.setNum(transdrug.getNum());
			
			recordList.add(record);
		}

		recordMapper.insertList(recordList);
	}
	
	public List<SalesRecord> getRecordBySeller(Integer sellerid, int pageIndex, int pageSize) {

		Example ex = new Example(SalesRecord.class);
		ex.createCriteria().andEqualTo("sellerid", sellerid);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds(pageIndex*pageSize, pageSize);
		List<SalesRecord> list = recordMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return list;
	}

	

}
