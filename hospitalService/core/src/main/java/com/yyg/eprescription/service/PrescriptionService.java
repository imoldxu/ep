package com.yyg.eprescription.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yyg.eprescription.entity.CountPrescriptionInfo;
import com.yyg.eprescription.entity.Prescription;
import com.yyg.eprescription.entity.PrescriptionDrugs;
import com.yyg.eprescription.entity.PrescriptionNumber;
import com.yyg.eprescription.entity.SearchOption;
import com.yyg.eprescription.mapper.PrescriptionDrugsMapper;
import com.yyg.eprescription.mapper.PrescriptionMapper;
import com.yyg.eprescription.mapper.PrescriptionNumberMapper;
import com.yyg.eprescription.proxy.CommonHospitalProxy;
import com.yyg.eprescription.proxy.PlatformProxy;
import com.yyg.eprescription.proxy.WSAPIProxy;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class PrescriptionService {

	@Autowired
	PrescriptionMapper prescriptionMapper;
	@Autowired
	PrescriptionDrugsMapper pDrugMapper;
	@Autowired
	PrescriptionNumberMapper pNumberMapper;

	@Autowired
	DoctorDrugService doctorDrugService;
	@Autowired
	DiagnosisService diagnosisService;
	
	public Prescription init(String hospitalNum) throws Exception {
		if(hospitalNum.equalsIgnoreCase("-1")){
			Prescription p = new Prescription();
			p.setSn(generateSN());
			return p;
		}else{
			//TODO 从医院HIS系统中获得相关信息,武胜医院使用武胜的代码
			Prescription p = CommonHospitalProxy.getHospitalInfo(hospitalNum);
			if(p != null){
				p.setSn(generateSN());
				return p;
			}else{
				throw new Exception("诊断号错误，请检查就诊号信息");
			}
		}
	}
	
	public Prescription init(String type, String hospitalnumber) throws Exception {
		if(hospitalnumber.equalsIgnoreCase("-1")){
			Prescription p = new Prescription();
			p.setSn(generateSN());
			return p;
		}else{
			Prescription p = WSAPIProxy.getHospitalInfo(type, hospitalnumber);
			if(p != null){
				p.setSn(generateSN());
				return p;
			}else{
				throw new Exception("诊断号错误，请检查就诊号信息");
			}
		}
	}
	
	@Transactional
	public String open(Integer doctorid, Integer hopspitalid, String prescriptionInfo, String drugList, Prescription p, List<PrescriptionDrugs> drugs)
			throws Exception {
		Date now = new Date();
		p.setCreatetime(now);
		
		//检查是否有相同编号的处方签	
		Example ex = new Example(Prescription.class);
		ex.createCriteria().andEqualTo("sn", p.getSn());
		List<Prescription> pList = prescriptionMapper.selectByExample(ex);
		if(!pList.isEmpty()){
			//相同的处方，先删除，再插入
			throw new Exception("处方已存在，不可重复提交");
		}	
		prescriptionMapper.insert(p);
		Long pid = p.getId();
		
		for(PrescriptionDrugs pdrug : drugs){
			pdrug.setPrescriptionid(pid);
			
			doctorDrugService.add(doctorid, pdrug.getDrugid());
		}
		
		pDrugMapper.insertList(drugs);
		
		//加入诊断信息
		String dmsg = p.getDiagnosis();
		diagnosisService.add(dmsg);
		
		return PlatformProxy.commit2Server(doctorid, hopspitalid, prescriptionInfo ,drugList);
	}
	
	public Prescription getPrescriptionByID(Integer id) throws Exception {
		Prescription p = prescriptionMapper.selectByPrimaryKey(id);
		
		if(p == null){
			throw new Exception("请求的处方不存在");
		}
		
		Example ex = new Example(PrescriptionDrugs.class);
		ex.createCriteria().andEqualTo("prescriptionid", id);
		List<PrescriptionDrugs> drugList = pDrugMapper.selectByExample(ex);
		
		p.setDrugList(drugList);
		
		return p;
	}
	
	public List<Prescription> getPresciptionList(SearchOption searchOption){
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
			String startDate =UTCStringtODefaultString(searchOption.getStartdate());
			criteria = criteria.andGreaterThanOrEqualTo("createdate", startDate);
		}
		if(searchOption.getEnddate() != null && !searchOption.getEnddate().isEmpty()){
			String endDate =UTCStringtODefaultString(searchOption.getEnddate());
			criteria = criteria.andLessThanOrEqualTo("createdate", endDate);
		}
		if(searchOption.getState() != null && !searchOption.getState().isEmpty()){
			criteria = criteria.andEqualTo("state", searchOption.getState());
		}
		ex.setOrderByClause("id Desc");
		
		int pageIndex = searchOption.getPageindex().intValue();
		int maxSize = 50;
		RowBounds rowBounds = new RowBounds(pageIndex*maxSize, maxSize);
		List<Prescription> plist = prescriptionMapper.selectByExampleAndRowBounds(ex, rowBounds);

		return plist;
	}
	
	public List<CountPrescriptionInfo> countPrescription(String lastMonthStr){
		
		
		List<CountPrescriptionInfo> infoList = prescriptionMapper.countPrescription(lastMonthStr+"-1", lastMonthStr+"-31");
	
		return infoList;
	}
	
	private synchronized String generateSN(){
		Date now = new Date();
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    localSimpleDateFormat.setLenient(false);
	    String today = localSimpleDateFormat.format(now);
	    SimpleDateFormat strFormat = new SimpleDateFormat("yyMMdd");
	    strFormat.setLenient(false);
	    String todaynum = strFormat.format(now);    
	    Example ex = new Example(PrescriptionNumber.class);
		ex.createCriteria().andEqualTo("opendate", today);
		List<PrescriptionNumber> list = pNumberMapper.selectByExample(ex);
		if(list.isEmpty()){
			PrescriptionNumber number = new PrescriptionNumber();
			number.setNumber(1);
			number.setOpendate(now);
			pNumberMapper.insert(number);
			return todaynum+formatNumber(number.getNumber());
		}else{
			PrescriptionNumber number = list.get(0);
			number.setNumber(number.getNumber()+1);
			pNumberMapper.updateByPrimaryKey(number);
			return todaynum+formatNumber(number.getNumber());
		}
	}
	
	private static final String STR_FORMAT = "0000"; 

	private String formatNumber(Integer intHao){
	    DecimalFormat df = new DecimalFormat(STR_FORMAT);
	    return df.format(intHao);
	}
	
	private static String UTCStringtODefaultString(String UTCString) {
        UTCString = UTCString.replace("Z", " UTC");
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
        SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
		try {
			date = utcFormat.parse(UTCString);
           return defaultFormat.format(date);

		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
 	    
	}
}
