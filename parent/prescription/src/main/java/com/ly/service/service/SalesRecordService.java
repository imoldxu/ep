
package com.ly.service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.TransactionDrug;
import com.ly.service.entity.Order;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.Store;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.feign.client.UserClient;
import com.ly.service.mapper.SalesRecordMapper;
import com.ly.service.utils.DateUtils;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.RedissonUtil;

import tk.mybatis.mapper.entity.Example;

@Service
public class SalesRecordService {

	@Autowired
	SalesRecordMapper recordMapper;
	@Autowired
	DrugClient drugClient;
	@Autowired
	UserClient userClient;
	@Autowired
	AccountService accountService;
	@Autowired
	RedissonUtil redissonUtil;
	
	public List<SalesRecord> getRecordByStore(Integer storeid, int pageIndex, int maxSize){
		Example ex = new Example(SalesRecord.class);
		ex.createCriteria().andEqualTo("storeid", storeid);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*maxSize, maxSize);
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
			throw new HandleException(ErrorCode.DATA_ERROR, "内部数据异常");
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
			record.setTotalsellerfee(transdrug.getSellerfee()*transdrug.getNum());
			
			record.setHospitalid(transdrug.getHospitalid());
			record.setHospitalname(transdrug.getHospitalname());
			
			record.setOrderid(order.getId());
			record.setPrescriptionid(transdrug.getPrescriptionid());
			
			ObjectMapper om = new ObjectMapper();
			StoreDrug storeDrug = om.convertValue(drugClient.getDrugByStore(storeid, transdrug.getDrugid()).fetchOKData(), StoreDrug.class);
			record.setSettlementprice(storeDrug.getSettlementprice());
			record.setTotalsettlementprice(storeDrug.getSettlementprice()*transdrug.getNum());
			record.setStoreid(storeid);
			Store store = om.convertValue(userClient.getStore(storeid).fetchOKData(), Store.class);
			record.setStorename(store.getName());
			record.setNum(transdrug.getNum());
			
			recordList.add(record);
		}

		recordMapper.insertList(recordList);
			
		accountService.settleSalesRecords(recordList);//结算交易
	}

	@Transactional
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
	
	public List<SalesRecord> getRecordBySeller(Integer sellerid, String doctorName, String startDate, String endDate, int pageIndex, int pageSize) {
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
		Example ex = new Example(SalesRecord.class);
		if(doctorName==null || doctorName.isEmpty()){
			ex.createCriteria().andEqualTo("sellerid", sellerid).andBetween("createtime", startDate, endDate);	
		}else{
			ex.createCriteria().andEqualTo("sellerid", sellerid).andBetween("createtime", startDate, endDate).andEqualTo("doctorname", doctorName);	
		}
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<SalesRecord> list = recordMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return list;
	}

	public List<SalesRecord> getRecords(int pageIndex, int pageSize) {
		Example ex = new Example(SalesRecord.class);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<SalesRecord> list = recordMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return list;
	}

}
