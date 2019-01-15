package com.ly.service.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ly.service.context.TransactionDrug;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.SearchOption;
import com.ly.service.entity.Doctor;
import com.ly.service.entity.Hospital;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.entity.Order;
import com.ly.service.entity.Patient;
import com.ly.service.entity.Prescription;
import com.ly.service.entity.PrescriptionDrug;
import com.ly.service.entity.Seller;
import com.ly.service.entity.StoreDrug;
import com.ly.service.feign.client.DrugClient;
import com.ly.service.feign.client.UserClient;
import com.ly.service.mapper.PrescriptionDrugMapper;
import com.ly.service.mapper.PrescriptionMapper;
import com.ly.service.utils.DateUtils;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.RedissonUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("PrescriptionService")
public class PrescriptionService {

	@Autowired
	PrescriptionMapper pMapper;
	@Autowired
	PrescriptionDrugMapper drugMapper;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	UserClient userClient;
	@Autowired
	DrugClient drugClient;
	@Autowired
	RedissonUtil redissonUtil;
	
	public int open(Integer doctorid, Prescription p, List<PrescriptionDrug> drugList){
		//TODO 医院暂不在互联网上开，都在内网开
		return 1;
	}
	
	public Prescription receive(int uid, Long pid){
		Prescription p = getPrescriptionById(pid);
		
		if(p.getUserid() == null){
			p.setUserid(uid);
			pMapper.updateByPrimaryKey(p);
			//为用户添加患者信息	
			userClient.addPatient(uid, p.getPatientname(), p.getPatientsex(), p.getPatientphone(), Patient.TYPE_IDCARD, "", "");
			//FIXME:此处即使有异常也不抛出resp.getOKData();
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "处方已经被领取，不可被重复领取");
		}
		
		return p;
	}

	private Prescription getPrescriptionById(Long pid) {
		Example ex = new Example(Prescription.class);
		ex.createCriteria().andEqualTo("id", pid);
		Prescription p = pMapper.selectOneByExample(ex);
		return p;
	}
	
	public List<Prescription> getPrescriptionListByOption(SearchOption searchOption){
		Example ex = new Example(Prescription.class);
		Criteria criteria = ex.createCriteria();
		if(searchOption.getNumber() != null && !searchOption.getNumber().isEmpty()){
			criteria = criteria.andEqualTo("num", searchOption.getNumber());
		}
		if(searchOption.getDoctorname() != null && !searchOption.getDoctorname().isEmpty()){
			criteria = criteria.andEqualTo("doctorname", searchOption.getDoctorname());
		}
		if(searchOption.getDepartment() != null && !searchOption.getDepartment().isEmpty()){
			criteria = criteria.andEqualTo("department", searchOption.getDepartment());	
		}
		if(searchOption.getPatientname() != null && !searchOption.getPatientname().isEmpty()){
			criteria = criteria.andEqualTo("patientname", searchOption.getPatientname());		
		}
		if(searchOption.getPatientphone() != null && !searchOption.getPatientphone().isEmpty()){
			criteria = criteria.andEqualTo("patientphone", searchOption.getPatientphone());
		}
		if(searchOption.getStartdate() != null && !searchOption.getStartdate().isEmpty()){
			String startDate = DateUtils.UTCStringtODefaultString(searchOption.getStartdate());
			criteria = criteria.andGreaterThanOrEqualTo("createdate", startDate);
		}
		if(searchOption.getEnddate() != null && !searchOption.getEnddate().isEmpty()){
			String endDate = DateUtils.UTCStringtODefaultString(searchOption.getEnddate());
			criteria = criteria.andLessThanOrEqualTo("createdate", endDate);
		}
		if(searchOption.getState() != null && !searchOption.getState().isEmpty()){
			criteria = criteria.andEqualTo("state", searchOption.getState());
		}
		ex.setOrderByClause("id Desc");
		
		int pageIndex = searchOption.getPageindex().intValue();
		int maxSize = 50;
		RowBounds rowBounds = new RowBounds(pageIndex*maxSize, maxSize);
		List<Prescription> plist = pMapper.selectByExampleAndRowBounds(ex, rowBounds);
		
		return plist;
	}
	
	public Prescription getPrescriptionDetail(Long pid) throws HandleException{
		Prescription p = pMapper.selectByPrimaryKey(pid);
		if(p == null){
			throw new HandleException(ErrorCode.ARG_ERROR, "处方不存在");
		}
		
		Example ex = new Example(PrescriptionDrug.class);
		ex.createCriteria().andEqualTo("prescriptionid", pid);
		List<PrescriptionDrug> drugList = drugMapper.selectByExample(ex);
		
		p.setDrugList(drugList);
		
		return p;
	}

	@Transactional
	public void commit(int doctorid, int hospitalid, Prescription perscription, List<PrescriptionDrug> drugList) {	
		//检查是否有相同编号的处方签
		Example ex = new Example(Prescription.class);
		ex.createCriteria().andEqualTo("sn", perscription.getSn()).andEqualTo("hospitalid", hospitalid);
		Prescription p = pMapper.selectOneByExample(ex);
		if(p!=null){
			//相同的处方，先删除，再插入 
			//Example drugEx = new Example(PrescriptionDrug.class);
			//drugEx.createCriteria().andEqualTo("prescriptionid", pList.get(0).getId());
			//drugMapper.deleteByExample(drugEx);
			
			//pMapper.delete(pList.get(0));
			//FIXME：相同的处方不允许重复提交
			throw new HandleException(ErrorCode.RECOMMIT_ERROR, "处方已提交，请勿重复提交");
		}
		Date now = new Date();
		perscription.setCreatetime(now);
		ObjectMapper om = new ObjectMapper();
		//TODO:给处方设置医院名称
		
		//FIXME:医生的id，要根据医院、医生姓名、以及科室来提取，或者由医院传入医生的id
		perscription.setDoctorid(doctorid);
		Doctor doctor = om.convertValue(userClient.getDoctor(doctorid).fetchOKData(), Doctor.class);
		perscription.setDoctorname(doctor.getName());
		perscription.setDepartment(doctor.getDepartment());
		perscription.setHospitalid(hospitalid);
		Hospital hospital = om.convertValue(userClient.getHospital(hospitalid).fetchOKData(), Hospital.class);
		perscription.setHospitalname(hospital.getName());
		
		pMapper.insertUseGeneratedKeys(perscription);
		Long pid = perscription.getId();
		
		for(PrescriptionDrug pdrug : drugList){
			pdrug.setPrescriptionid(pid);
			HospitalDrug hospitalDrug = om.convertValue(drugClient.getHospitalDrug(pdrug.getDrugid(), perscription.getHospitalid()).fetchOKData(), HospitalDrug.class);
			//FIXME:药名名称，规格从系统获取
			pdrug.setSoldnumber(0);
			pdrug.setDrugname(hospitalDrug.getDrugname());
			pdrug.setStandard(hospitalDrug.getDrugstandard());
			pdrug.setSellfee(hospitalDrug.getSellfee());
			pdrug.setSellerid(hospitalDrug.getSellerid());
		}
		drugMapper.insertList(drugList);
	}

	@Transactional
	public Order buy(Integer uid, Long pid, List<TransactionDrug> buyList) {
		int amount = 0;
		Prescription p = null;
		boolean islock = redissonUtil.tryLock("BUY_PRESCRIPTION_"+pid, TimeUnit.MILLISECONDS, 1000, 1500);
		if(islock){
			p = getPrescriptionById(pid);
			if(p.getUserid() != uid){
				throw new HandleException(ErrorCode.ARG_ERROR, "你无权进行此操作");
			}
			
			Date now = new Date();
			Date createDate = p.getCreatetime();
			//TODO 处方有效期不得超过3天
			int i = now.compareTo(createDate);
			if(i>3){
				throw new HandleException(ErrorCode.NORMAL_ERROR, "处方已过期");
			}
			
			List<PrescriptionDrug> pDrugList = getDrugListByPrescriptionID(pid);		
			
			//校验处方中药品的数量是否满足
			for(TransactionDrug bd : buyList){
				boolean isMatch = false;
				for(PrescriptionDrug pd : pDrugList){
					if(bd.getDrugid() == pd.getDrugid()){
						if(bd.getNum()> pd.getNumber()-pd.getSoldnumber()){
							throw new HandleException(ErrorCode.NORMAL_ERROR, "购买数量不得超过处方数量");
						}
					    bd.setDoctorid(p.getDoctorid());
					    bd.setHospitalid(p.getHospitalid());
					    pd.setSoldnumber(pd.getSoldnumber()+bd.getNum());
						isMatch = true;
	
						drugMapper.updateByPrimaryKey(pd);
						break;
					}
				}
				if(!isMatch){
					throw new HandleException(ErrorCode.NORMAL_ERROR, "不可选购超过处方的药品");
				}
			}
			
			redissonUtil.unlock("BUY_PRESCRIPTION_"+pid);
		}else{
			throw new HandleException(ErrorCode.LOCK_ERROR, "系统异常");
		}
		//String TransactionListStr = JSONUtils.getJsonString(buyList);
		//创建订单
		Order order  = orderService.create(p.getUserid(), amount, buyList);
		return order;
		
	}
	
	private List<PrescriptionDrug> getDrugListByPrescriptionID(Long pid){
		Example ex = new Example(PrescriptionDrug.class);
		ex.createCriteria().andEqualTo("prescriptionid", pid);
		return drugMapper.selectByExample(ex);
	}

	@Transactional
	public Order buyFromStore(Integer storeid, Long pid, List<TransactionDrug> transList) {
		ObjectMapper om = new ObjectMapper();
		Prescription p = null;
		boolean islock = redissonUtil.tryLock("BUY_PRESCRIPTION_"+pid, TimeUnit.MILLISECONDS, 1000, 1500);
		if(islock){
			p = getPrescriptionById(pid);
			Date now = new Date();
			Date createDate = p.getCreatetime();
			//TODO 处方有效期不得超过3天
			int i = now.compareTo(createDate);
			if(i>3){
				throw new HandleException(-1, "处方已过期");
			}
			
			List<PrescriptionDrug> pDrugList = getDrugListByPrescriptionID(pid);
			
			//校验处方中药品的数量是否满足
			for(TransactionDrug tDrug : transList){
				boolean isMatch = false;
				for(PrescriptionDrug pDrug : pDrugList){
					if(tDrug.getDrugid() == pDrug.getDrugid()){
						if(tDrug.getNum()> pDrug.getNumber()-pDrug.getSoldnumber()){
							throw new HandleException(-1, "购买数量不得超过处方数量");
						}
						tDrug.setPrescriptionid(pid);
					    tDrug.setDoctorid(p.getDoctorid());
					    tDrug.setDoctorname(p.getDoctorname());
					    tDrug.setHospitalid(p.getHospitalid());
					    //FIXME:获取医院名称
					    Hospital hospital = om.convertValue(userClient.getHospital(p.getHospitalid()).fetchOKData(), Hospital.class);
					    tDrug.setHospitalname(hospital.getName());
					    
					    tDrug.setDrugname(pDrug.getDrugname());
					    tDrug.setSellerfee(pDrug.getSellfee());
					    tDrug.setSellerid(pDrug.getSellerid());
					    //FIXME:获取销售胡名字
					    Seller seller = om.convertValue(userClient.getSeller(pDrug.getSellerid()).fetchOKData(), Seller.class);
					    tDrug.setSellername(seller.getName());
					    
					    pDrug.setSoldnumber(pDrug.getSoldnumber()+tDrug.getNum());
						isMatch = true;
	
						drugMapper.updateByPrimaryKey(pDrug);
						break;
					}
				}
				if(!isMatch){
					throw new HandleException(-1, "不可选购超过处方的药品");
				}
			}
			redissonUtil.unlock("BUY_PRESCRIPTION_"+pid);
		}else{
			throw new HandleException(ErrorCode.LOCK_ERROR, "系统异常");
		}

		Order order = orderService.createByStore(storeid, transList);
		return order;
	}

	public Prescription getPrescriptionDetailByStore(Integer storeid, Long pid) {
		Prescription p = getPrescriptionDetail(pid);
		List<Integer> drugs = new ArrayList<Integer>();
		List<PrescriptionDrug> drugList =  p.getDrugList();
		for(PrescriptionDrug drug: drugList){
			drugs.add(drug.getDrugid());
		}
		String drugsStr = JSONUtils.getJsonString(drugs);
		ObjectMapper om = new ObjectMapper();
		List<StoreDrug> storeDrugList = om.convertValue(drugClient.getDrugsInStore(storeid, drugsStr).fetchOKData(), new TypeReference<List<StoreDrug>>() {});
		
		//药房显示的处方仅包含药房有的药品
		
		for(int i=(drugList.size()-1); i>=0; i--){
			PrescriptionDrug drug = drugList.get(i);
//			if(!storeDrugList.contains(drug.getDrugid())){
//				drugList.remove(drug);
//			}
			boolean isNeedRemove = true;
			for(StoreDrug storeDrug : storeDrugList){
				if(storeDrug.getDrugid().equals(drug.getDrugid())){
					isNeedRemove = false;
					break;
				}
			}
			if(isNeedRemove){
				drugList.remove(drug);
			}
		}
		return p;
	}

	public List<Prescription> getPrescriptionListByUser(Integer uid, int pageIndex, int pageSize) {
		Example ex = new Example(Prescription.class);
		ex.createCriteria().andEqualTo("userid", uid);
		ex.setOrderByClause("id DESC");
		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize, pageSize);
		List<Prescription> plist = pMapper.selectByExampleAndRowBounds(ex, rowBounds);
		return plist;
	}

	public List<Prescription> getStorePrescriptions(Integer storeid, int pageIndex, int pageSize) {
		int offset = (pageIndex-1)*pageSize;
		List<Prescription> ret = pMapper.getStorePrescripts(storeid, offset, pageSize);
		return ret;
	}
	
	public Prescription getStorePrescriptionDetail(Integer storeid, Long pid) {		
		Prescription p = getPrescriptionById(pid);
		if(p == null){
			throw new HandleException(ErrorCode.ARG_ERROR, "参数错误");
		}
		List<PrescriptionDrug> ret = drugMapper.getPrescriptDrugsByStore(storeid, pid);
		if(ret.isEmpty()){
			throw new HandleException(ErrorCode.NORMAL_ERROR, "该处方未在你的药店购药"); 
		}
		p.setDrugList(ret);
		return p;
	}

	public Prescription getUserPrescriptionDetail(Integer uid, Long pid) {
		
		Prescription p = getPrescriptionDetail(pid);
		if(p.getUserid() != uid){
			throw new HandleException(ErrorCode.DOMAIN_ERROR, "你无权进行此操作");
		}
		return p;
	}
}
