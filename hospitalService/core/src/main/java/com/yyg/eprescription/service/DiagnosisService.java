package com.yyg.eprescription.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yyg.eprescription.entity.DiagnosisMsg;
import com.yyg.eprescription.mapper.DiagnosisMsgMapper;
import com.yyg.eprescription.util.ChineseCharacterUtil;

@Service
public class DiagnosisService {
	
	@Autowired
	DiagnosisMsgMapper msgMapper;

	public List<String> getDiagnosisByKey(String keys){
		keys = keys.toUpperCase();
		List<String> ret = msgMapper.getMsgByKeys(keys+"%");
		
		return ret;
	}
	
	public void add(String dmsg){
		List<String> msgList = msgMapper.getMsgByKeys(dmsg);	
		if(msgList.isEmpty()){
			DiagnosisMsg msg = new DiagnosisMsg();
			msg.setDiagnosis(dmsg);
			String fullKeys = ChineseCharacterUtil.convertHanzi2Pinyin(dmsg, true);
			msg.setFullkeys(fullKeys);
			String shortKeys = ChineseCharacterUtil.convertHanzi2Pinyin(dmsg, false);
			msg.setShortkeys(shortKeys);
			msgMapper.insert(msg);
		}
	}
}
