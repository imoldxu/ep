package com.yyg.eprescription.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yyg.eprescription.context.HandleException;
import com.yyg.eprescription.entity.DoctorDrug;
import com.yyg.eprescription.entity.Drug;
import com.yyg.eprescription.entity.ShortDrugInfo;
import com.yyg.eprescription.mapper.DoctorDrugMapper;
import com.yyg.eprescription.mapper.DrugMapper;
import com.yyg.eprescription.proxy.PlatformProxy;
import com.yyg.eprescription.util.ExcelUtils;
import tk.mybatis.mapper.entity.Example;

@Service
public class DrugService {

	private static final boolean isUseNative = false;
	
	@Autowired
	DrugMapper drugMapper;
	@Autowired
	DoctorDrugMapper doctorDrugsMapper;
	
	public List<ShortDrugInfo> getDrugsByKeys(String keys, int type) {
		
		if(isUseNative){
			List<ShortDrugInfo> ret = new ArrayList<ShortDrugInfo>();
			
			if(type == 1){
				keys = keys.toUpperCase();
				List<ShortDrugInfo> matchList = drugMapper.getDrugsByKeys(keys);
				if(matchList.isEmpty()){
					ret = drugMapper.getDrugsByKeys("%"+keys+"%");	
				}else{
					ret.addAll(matchList);
					Integer myid = matchList.get(0).getId();
					List<ShortDrugInfo> druglist = drugMapper.getDrugsByKeysWithoutID(myid, "%"+keys+"%");
					ret.addAll(druglist);
				}
			}else{
				keys = keys.toUpperCase();
				List<ShortDrugInfo> matchList = drugMapper.getZyDrugsByKeys(keys);
				if(matchList.isEmpty()){
					ret = drugMapper.getZyDrugsByKeys("%"+keys+"%");	
				}else{
					ret.addAll(matchList);
					Integer myid = matchList.get(0).getId();
					List<ShortDrugInfo> druglist = drugMapper.getZyDrugsByKeysWithoutID(myid, "%"+keys+"%");
					ret.addAll(druglist);
				}
			}
			return ret;
		}else{
			List<ShortDrugInfo> ret = PlatformProxy.getDrugsByKeys(keys, type);
			return ret;
		}
	}
	
	public List<Drug> getDrugInfoListByKeys(String keys) {
		List<Drug> ret = new ArrayList<Drug>();

		keys = keys.toUpperCase();
		
		Example ex = new Example(Drug.class);
		ex.or().andLike("drugname", "%"+keys+"%");
		ex.or().andLike("fullkeys", "%"+keys+"%");
		ex.or().andLike("shortnamekeys", "%"+keys+"%");
		ret = drugMapper.selectByExample(ex);
		
		return ret;
	}
	
	public List<ShortDrugInfo> getDrugsByTag(String tag, int type) {
		if(isUseNative){
			List<ShortDrugInfo> ret = null;
			if(type==1){
				ret = drugMapper.getDrugByTag(tag);
			}else{
				ret = drugMapper.getZyDrugByTag(tag);	
			}
			return ret;
		}else{
			List<ShortDrugInfo> ret = PlatformProxy.getDrugsByTag(tag, type);
			return ret;
		}
	}
	
	public List<ShortDrugInfo> getDrugsByDoctor(Integer doctorid, int type) {
		if(isUseNative){
			List<ShortDrugInfo> ret = null;
			if(1 == type){
				ret = doctorDrugsMapper.getDrugsByDoctor(doctorid);
			}else{
				ret = doctorDrugsMapper.getZyDrugsByDoctor(doctorid);
			}
			
			return ret;
		}else{
			List<ShortDrugInfo> ret = PlatformProxy.getDrugsByDoctor(doctorid, type);
			return ret;
		}
	}
	
	public Drug getDrugByID(Integer drugid) throws Exception{
		if(isUseNative){
			Drug drug = drugMapper.selectByPrimaryKey(drugid);
			if(drug==null){
				throw new HandleException(2, "请求的药品不存在或已下架");
			}
			return drug;
		}else{
			Drug drug = PlatformProxy.getDrugById(drugid);
			return drug;
		}
	}
	
	public List<Drug> uploadByExcel(MultipartFile file) {
		
		if(file==null){
			throw new HandleException(3, "请求参数异常");
		}
		
		//获取文件名
	    String name=file.getOriginalFilename();
	    //进一步判断文件是否为空（即判断其大小是否为0或其名称是否为null）
	    long size=file.getSize();
	    if(name == null || ("").equals(name) && size==0){
	    	throw new HandleException(2, "文件不存在或没有内容");
	    }
	    //批量导入。参数：文件名，文件。
	    ExcelUtils excelUtils = new ExcelUtils();
	    
	    try{
	    	List<Drug> drugList = excelUtils.getExcelInfo(name, file);
	    	//drugMapper.insertDrugs(drugList);
			drugMapper.insertList(drugList);
	    	return drugList;    
	    }catch(IOException ioe){
	    	throw new HandleException(2, ioe.getMessage());
	    }catch (Exception e) {
	    	e.printStackTrace();
	    	throw new HandleException(2, "导入失败");
	    }
	}
	
	public Drug modifyDrug(Drug drug) throws Exception {
		
		if(drug.getId() != null){
			int opRet = drugMapper.updateByPrimaryKey(drug);
			if(opRet == 0){
				throw new Exception("修改失败");    
			}else{
				return drug;    
			}
		}else{
			throw new Exception("药品不存在");
		}
	}
	
	public void delDrug(int drugid) throws Exception {
		
		int optRet = drugMapper.deleteByPrimaryKey(drugid);
	
		if(optRet!=0){
			Example ex = new Example(DoctorDrug.class);
			ex.createCriteria().andEqualTo("drugid", drugid);
			doctorDrugsMapper.deleteByExample(ex);
			
			return;
		}else{
			throw new Exception("药品不存在");
		}
	}
	
	public int downDrug(int drugid) throws Exception {
		Drug drug = getDrugByID(drugid);
		drug.setState(Drug.STATE_EMPTY);
		return drugMapper.updateByPrimaryKey(drug);
	}
	
	public int upDrug(int drugid) throws Exception {
		Drug drug = getDrugByID(drugid);
		drug.setState(Drug.STATE_OK);
		return drugMapper.updateByPrimaryKey(drug);
		
	}
}
