package com.ly.service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.context.SimpleDrugInfo;
import com.ly.service.entity.HospitalDrug;
import com.ly.service.mapper.HospitalDrugMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class HospitalDrugService {

	@Autowired
	HospitalDrugMapper drugMapper;
	
	public HospitalDrug addHospitalDrug(int exid, int drugid, String drugname, String drugstandard, String drugcompany, int hospitalid, String hospitalname){
		Example ex = new Example(HospitalDrug.class);
		ex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("hospitalid", hospitalid);
		HospitalDrug hdrug = drugMapper.selectOneByExample(ex);
		if(hdrug ==  null){
			hdrug = new HospitalDrug();
			hdrug.setHospitalid(hospitalid);
			hdrug.setHospitalname(hospitalname);
			hdrug.setDrugid(drugid);
			hdrug.setDrugname(drugname);
			hdrug.setDrugstandard(drugstandard);
			hdrug.setDrugcompany(drugcompany);
			hdrug.setExid(exid);
			drugMapper.insertUseGeneratedKeys(hdrug);
		}else{
			throw new HandleException(ErrorCode.NORMAL_ERROR, "药品已存在，无需重复添加");
		}
		return hdrug;
	}
	
//	public List<HospitalDrug> getHospitalDrugListBySeller(int sellerid, String key, int pageIndex, int pageSize){
//		Example ex = new Example(HospitalDrug.class);
//		if(key != null && !key.isEmpty()){
//			ex.createCriteria().andEqualTo("sellerid", sellerid).andLike("drugname", "%"+key+"%");		
//		}else{
//			ex.createCriteria().andEqualTo("sellerid", sellerid);	
//		}
//		ex.setOrderByClause("id DESC");
//		RowBounds rowBounds = new RowBounds((pageIndex-1)*pageSize,pageSize);
//		List<HospitalDrug> list = drugMapper.selectByExampleAndRowBounds(ex, rowBounds);
//		return list;
//	}

	public HospitalDrug getHospitalDrug(int drugid, int hospitalid) {
		Example ex = new Example(HospitalDrug.class);
		ex.createCriteria().andEqualTo("drugid", drugid).andEqualTo("hospitalid", hospitalid);
		HospitalDrug sd = drugMapper.selectOneByExample(ex);
		return sd;
	}

	public void modifyHospitalDrug(HospitalDrug hospitalDrug) {
		drugMapper.updateByPrimaryKey(hospitalDrug);
	}

	public void del(Long hdid) {
		drugMapper.deleteByPrimaryKey(hdid);
	}

	public List<SimpleDrugInfo> getSimpleDrugListByKeys(Integer hid, int type, String keys) {
		List<SimpleDrugInfo> ret = new ArrayList<SimpleDrugInfo>();
		
		if(type == 1){
			keys = keys.toUpperCase();
			List<SimpleDrugInfo> matchList = drugMapper.getDrugsByKeys(hid, keys);
			if(matchList.isEmpty()){
				ret = drugMapper.getDrugsByKeys(hid, "%"+keys+"%");	
			}else{
				ret.addAll(matchList);
				Integer myid = matchList.get(0).getId();
				List<SimpleDrugInfo> druglist = drugMapper.getDrugsByKeysWithoutID(hid, myid, "%"+keys+"%");
				ret.addAll(druglist);
			}
		}else{
			keys = keys.toUpperCase();
			List<SimpleDrugInfo> matchList = drugMapper.getZyDrugsByKeys(hid, keys);
			if(matchList.isEmpty()){
				ret = drugMapper.getZyDrugsByKeys(hid, "%"+keys+"%");	
			}else{
				ret.addAll(matchList);
				Integer myid = matchList.get(0).getId();
				List<SimpleDrugInfo> druglist = drugMapper.getZyDrugsByKeysWithoutID(hid, myid, "%"+keys+"%");
				ret.addAll(druglist);
			}
		}
		
		return ret;
	}

	public List<SimpleDrugInfo> getSimpleDrugListByTag(Integer hid, int type, String tag) {
		List<SimpleDrugInfo> ret = null;
		//先根据tag获取tag标签id，再获取该标签下的药品id,再根据药品id获取药品信息
		if(type==1){
			ret = drugMapper.getDrugByTag(hid, tag);
		}else{
			ret = drugMapper.getZyDrugByTag(hid, tag);	
		}
		return ret;
	}

	public List<HospitalDrug> getHospitalDrugsByHospital(Integer hospitalid, String key, Integer pageIndex,
			Integer pageSize) {
		int offset = (pageIndex-1)*pageSize;
		key = '%'+key+'%';
		List<HospitalDrug> ret = drugMapper.getHospitalDrugsByKeys(hospitalid, key, offset, pageSize);
		return ret;
	}

}
