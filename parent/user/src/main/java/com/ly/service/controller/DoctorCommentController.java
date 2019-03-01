package com.ly.service.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.DoctorComment;
import com.ly.service.service.DoctorCommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/doctor/comment")
@Api("医生评论")
public class DoctorCommentController {

	@Autowired
	DoctorCommentService commentService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getAvgGrade", method = RequestMethod.GET)
	@ApiOperation(value = "获取医生综合评分", notes = "通用接口")
	public Response getAvgGrade(@ApiParam(name="doctorid", value="医生id") @RequestParam(name="doctorid") Integer doctorid,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			Double ret = commentService.getAvgStar(doctorid);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getCommentsByDoctor", method = RequestMethod.GET)
	@ApiOperation(value = "获取医生评论", notes = "通用接口")
	public Response getCommentsByDoctor(@ApiParam(name="doctorid", value="医生id") @RequestParam(name="doctorid") Integer doctorid,
			@ApiParam(name="pageIndex", value="页数") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request, HttpServletResponse response){
		try {
			
			List<DoctorComment> ret = commentService.getCommentsByDoctor(doctorid, pageIndex, pageSize);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
}
