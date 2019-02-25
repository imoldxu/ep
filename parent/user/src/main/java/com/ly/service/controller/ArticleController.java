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
import com.ly.service.entity.Article;
import com.ly.service.service.ArticleService;
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
			
			List<Article> ret = articleService.getArticleList(uid, pageIndex, pageSize);
			
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
	@ApiOperation(value = "获取文章", notes = "用户接口")
	public Response getArticleById(@ApiParam(name="articleId", value="文章id") @RequestParam(name="articleId") int articleId,
			HttpServletRequest request,
			HttpServletResponse response){
		try {
			Integer uid = SessionUtil.getUserId(request);
			
			Article ret = articleService.getArticleById(articleId);
			
			return Response.OK(ret);
		}catch (HandleException e) {
			return Response.Error(e.getErrorCode(), e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			return Response.SystemError();
		}
		
	}
	
}
