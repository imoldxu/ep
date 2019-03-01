
package com.ly.service.service;

import java.math.BigDecimal;
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
import com.ly.service.entity.Prescription;
import com.ly.service.entity.PrescriptionDrug;
import com.ly.service.entity.SalesRecord;
import com.ly.service.entity.Store;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.feign.client.UserClient;
import com.ly.service.mapper.SalesRecordMapper;
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
	@Autowired
	PrescriptionService prescriptionService;
	
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
			
			record.setExid(transdrug.getExid());
			
			record.setHospitalid(transdrug.getHospitalid());
			record.setHospitalname(transdrug.getHospitalname());
			
			record.setOrderid(order.getId());
			record.setPrescriptionid(transdrug.getPrescriptionid());
			
			Store store = userClient.getStore(storeid).fetchOKData(Store.class);
			record.setStorename(store.getName());
			record.setNum(transdrug.getNum());
			double rate = store.getRate();
			
			//FIXME 采用费率计算出结算价格，而没有采用单个药品的结算费用，此方案在调整费率时比较容易实现，采用单个药品的结算费用更灵活多变
			StoreDrug storeDrug = drugClient.getDrugByStore(storeid, transdrug.getDrugid()).fetchOKData(StoreDrug.class);
			record.setPrice(storeDrug.getPrice());
			record.setSettlementprice(getInt(storeDrug.getPrice()*rate));
			record.setTotalsettlementprice(record.getSettlementprice()*transdrug.getNum());
			record.setStoreid(storeid);
			
			recordList.add(record);
		}

		recordMapper.insertList(recordList);
			
		accountService.settleSalesRecords(recordList);//结算交易
	}

	private int getInt(double number){
	    BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
	    return Integer.parseInt(bd.toString()); 
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
			
			record.setExid(transdrug.getExid());
			
			record.setHospitalid(transdrug.getHospitalid());
			record.setHospitalname(transdrug.getHospitalname());
			
			record.setOrderid(order.getId());
			record.setPrescriptionid(transdrug.getPrescriptionid());
			
			record.setNum(transdrug.getNum());
			
			recordList.add(record);
		}

		recordMapper.insertList(recordList);
		
		
	}
	
//	public List<SalesRecord> getRecordBySeller(Integer sellerid, String doctorName, String startDate, String endDate, int pageIndex, int pageSize) {
//		if(startDate==null || startDate.isEmpty()){
//			startDate = "1970-1-1";
//		}else{
//			startDate = DateUtils.UTCStringtODefaultString(startDate);
//		}
//		if(endDate == null || endDate.isEmpty()){
//			endDate = "2099-12-31";
//		}else{
//			endDate = DateUtils.UTCStringtODefaultString(endDate);
//		}
//		Example ex = new Example(SalesRecord.class);
//		if(doctorName==null || doctorName.isEmpty()){
//			ex.createCriteria().andEqualTo("sellerid", sellerid).andBetween("createtime", startDate, endDate);	
//		}else{
//			ex.createCriteria().andEqualTo("sellerid", sellerid).andBetween("createtime", startDate, endDate).andEqualTo("doctorname", doctorName);	
//		}
//		ex.setOrderByClause("id DESC");
//		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
//		List<SalesRecord> list = recordMapper.selectByExampleAndRowBounds(ex, rowBounds);
//		return list;
//	}

	public List<SalesRecord> getRecords(int pageIndex, int pageSize) {
		Example ex = new Example(SalesRecord.class);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<SalesRecord> list = recordMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return list;
	}

	@Transactional
	public void refund(Integer storeid, Long pid, List<TransactionDrug> refundDrugs) {
		List<SalesRecord> refundRecordList = new ArrayList<SalesRecord>();
		
		for(TransactionDrug refundDrug : refundDrugs) {
			int refundNum = refundDrug.getNum();
			List<SalesRecord> toRefundRecordList = recordMapper.getSalesRecordForRefund(storeid, refundDrug.getDrugid(), pid);
			for(SalesRecord toRefundRecord:toRefundRecordList) {
				int canRefundNum = toRefundRecord.getNum()-toRefundRecord.getRefundnum();
				if(canRefundNum>=refundNum) {					
					toRefundRecord.setRefundnum(toRefundRecord.getRefundnum()+refundNum);
					recordMapper.updateByPrimaryKey(toRefundRecord);
					
					SalesRecord refundRecord = new SalesRecord();
					refundRecord.setCreatetime(new Date());
					refundRecord.setDoctorid(toRefundRecord.getDoctorid());
					refundRecord.setDoctorname(toRefundRecord.getDoctorname());
					refundRecord.setDrugid(toRefundRecord.getDrugid());
					refundRecord.setDrugname(toRefundRecord.getDrugname());
					refundRecord.setExid(toRefundRecord.getExid());
					refundRecord.setHospitalid(toRefundRecord.getHospitalid());
					refundRecord.setHospitalname(toRefundRecord.getHospitalname());
					refundRecord.setNum(0-refundNum);
					refundRecord.setOrderid(null);
					refundRecord.setPrescriptionid(toRefundRecord.getPrescriptionid());
					refundRecord.setPrice(toRefundRecord.getPrice());
					refundRecord.setRefundnum(0);
					refundRecord.setSettlementprice(toRefundRecord.getSettlementprice());
					refundRecord.setStoreid(toRefundRecord.getStoreid());
					refundRecord.setStorename(toRefundRecord.getStorename());
					refundRecord.setTotalsettlementprice(0- refundRecord.getSettlementprice()*refundNum);
					recordMapper.insert(refundRecord);
					
					refundRecordList.add(refundRecord);
					break;
				}else {
					refundNum = refundNum - canRefundNum;
					
					toRefundRecord.setRefundnum(toRefundRecord.getRefundnum()+canRefundNum);
					recordMapper.updateByPrimaryKey(toRefundRecord);
					
					SalesRecord refundRecord = new SalesRecord();
					refundRecord.setCreatetime(new Date());
					refundRecord.setDoctorid(toRefundRecord.getDoctorid());
					refundRecord.setDoctorname(toRefundRecord.getDoctorname());
					refundRecord.setDrugid(toRefundRecord.getDrugid());
					refundRecord.setDrugname(toRefundRecord.getDrugname());
					refundRecord.setExid(toRefundRecord.getExid());
					refundRecord.setHospitalid(toRefundRecord.getHospitalid());
					refundRecord.setHospitalname(toRefundRecord.getHospitalname());
					refundRecord.setNum(0-canRefundNum);
					refundRecord.setOrderid(null);
					refundRecord.setPrescriptionid(toRefundRecord.getPrescriptionid());
					refundRecord.setPrice(toRefundRecord.getPrice());
					refundRecord.setRefundnum(0);
					refundRecord.setSettlementprice(toRefundRecord.getSettlementprice());
					refundRecord.setStoreid(toRefundRecord.getStoreid());
					refundRecord.setStorename(toRefundRecord.getStorename());
					refundRecord.setTotalsettlementprice(0- refundRecord.getSettlementprice()*canRefundNum);
					recordMapper.insert(refundRecord);
					
					refundRecordList.add(refundRecord);
				}
			}
		}

		accountService.settleSalesRecords(refundRecordList);
	}

}
