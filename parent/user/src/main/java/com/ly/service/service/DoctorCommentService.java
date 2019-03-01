package com.ly.service.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;
import com.ly.service.entity.DoctorComment;
import com.ly.service.mapper.DoctorCommentMapper;


@Service
public class DoctorCommentService {

	@Autowired
	DoctorCommentMapper mapper;
	
	public void commitService(String content, Integer uid, Integer doctorid, Integer star) {
		if(star>5 || star < 0) {
			throw new HandleException(ErrorCode.ARG_ERROR, "评星参数错误");
		}
		
		DoctorComment comment = new DoctorComment();
		comment.setContent(content);
		comment.setCreatetime(new Date());
		comment.setDoctorid(doctorid);
		comment.setStar(star);
		comment.setUid(uid);
		
		mapper.insert(comment);
	}
	
	public List<DoctorComment> getCommentsByDoctor(Integer doctorid, int pageIndex, int pageSize){
		List<DoctorComment> ret = mapper.getCommentsByDoctor(doctorid, (pageIndex-1)*pageSize, pageSize);
		return ret;
	}
	
	public Double getAvgStar(Integer doctorid) {
		Double avgstar = mapper.getAvgStar(doctorid);
		if(avgstar == null) {
			avgstar = 0d;
		}
		return avgstar;
	}
}
