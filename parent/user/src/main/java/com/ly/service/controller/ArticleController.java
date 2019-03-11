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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ly.service.context.HandleException;
import com.ly.service.context.Response;
import com.ly.service.entity.Article;
import com.ly.service.service.ArticleService;
import com.ly.service.utils.JSONUtils;
import com.ly.service.utils.SessionUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/article")
@Api("文章")
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getArticleList", method = RequestMethod.GET)
	@ApiOperation(value = "获取文章列表", notes = "获取文章列表")
	public Response getArticleList(@ApiParam(name="pageIndex", value="页数") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			Integer uid = SessionUtil.getUserId(request);
			
			List<Article> ret = articleService.getUserArticleList(uid, pageIndex, pageSize);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getArticleById", method = RequestMethod.GET)
	@ApiOperation(value = "获取文章", notes = "通用接口")
	public Response getArticleById(@ApiParam(name="articleId", value="文章id") @RequestParam(name="articleId") int articleId,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			
			Article ret = articleService.getArticleById(articleId);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/publish", method = RequestMethod.POST)
	@ApiOperation(value = "发表文章列表", notes = "管理接口")
	public Response publish(@ApiParam(name="title", value="标题") @RequestParam(name="title") String title,
			@ApiParam(name="piclist", value="列表页图片") @RequestParam(name="piclist") String piclist,
			@ApiParam(name="content", value="内容") @RequestParam(name="content") String content,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			SessionUtil.getManagerId(request);
			
			JSONArray picArray = JSON.parseArray(piclist);
			Article ret = articleService.publish(title, picArray ,content);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/modify", method = RequestMethod.POST)
	@ApiOperation(value = "编辑文章", notes = "管理接口")
	public Response modify(@ApiParam(name="articleId", value="文章id") @RequestParam(name="articleId") Integer articleId,
			@ApiParam(name="title", value="标题") @RequestParam(name="title") String title,
			@ApiParam(name="piclist", value="列表页图片") @RequestParam(name="piclist") String piclist,
			@ApiParam(name="content", value="内容") @RequestParam(name="content") String content,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			SessionUtil.getManagerId(request);
			
			JSONArray picArray = JSON.parseArray(piclist);
			Article ret = articleService.modify(articleId, title, picArray ,content);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/changeState", method = RequestMethod.POST)
	@ApiOperation(value = "上下架文章", notes = "管理接口")
	public Response changeState(@ApiParam(name="articleId", value="文章id") @RequestParam(name="articleId") Integer articleId,
			@ApiParam(name="state", value="1上架，0为下架") @RequestParam(name="state") int state,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			SessionUtil.getManagerId(request);
			Article ret = null;
			
			if(state == 1) {
				ret = articleService.up(articleId);
			}else {
				ret = articleService.down(articleId);
			}
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}	
	}
	
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
	@RequestMapping(path="/getArticles", method = RequestMethod.GET)
	@ApiOperation(value = "获取文章列表", notes = "管理接口")
	public Response getArticles(@ApiParam(name="title", value="标题") @RequestParam(name="title") String title,
			@ApiParam(name="pageIndex", value="页数") @RequestParam(name="pageIndex") int pageIndex,
			@ApiParam(name="pageSize", value="每页数量") @RequestParam(name="pageSize") int pageSize,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			SessionUtil.getManagerId(request);
			
			List<Article> ret = articleService.getArticleList(title, pageIndex, pageSize);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
}
