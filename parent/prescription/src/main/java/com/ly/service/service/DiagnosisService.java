package com.ly.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.entity.Diagnosis;
import com.ly.service.mapper.DiagnosisMapper;
import com.ly.service.utils.ChineseCharacterUtil;

@Service
public class DiagnosisService {
	
	@Autowired
	DiagnosisMapper mapper;

	public List<String> getDiagnosisByKey(String keys){
		keys = keys.toUpperCase();
		List<String> ret = mapper.getMsgByKeys(keys+"%");
		
		return ret;
	}
	
	public void add(String dmsg){
		List<String> msgList = mapper.getMsgByKeys(dmsg);	
		if(msgList.isEmpty()){
			Diagnosis msg = new Diagnosis();
			msg.setDiagnosis(dmsg);
			String fullKeys = ChineseCharacterUtil.convertHanzi2Pinyin(dmsg, true);
			msg.setFullkeys(fullKeys);
			String shortKeys = ChineseCharacterUtil.convertHanzi2Pinyin(dmsg, false);
			msg.setShortkeys(shortKeys);
			mapper.insert(msg);
		}
	}
}
