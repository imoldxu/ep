package com.ly.service.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ly.service.context.ErrorCode;
import com.ly.service.context.HandleException;

public class SessionUtil {

	public static void setDoctorId(HttpServletRequest request, Integer id){
		HttpSession session = request.getSession();
		session.setAttribute("DOCTOR_ID", id);
		session.setMaxInactiveInterval(7200);
	}

	public static Integer getDoctorId(HttpServletRequest request)  throws HandleException{
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("DOCTOR_ID");
		if(id==null){
			throw new HandleException(ErrorCode.SESSION_ERROR, "登录已过期,请重新登录");
		}
		return id;
	}
	
	public static void setUserId(HttpServletRequest request, Integer id) {
		HttpSession session = request.getSession();
		session.setAttribute("USER_ID", id);
		session.setMaxInactiveInterval(7200);
	}
	
	public static Integer getUserId(HttpServletRequest request)  throws HandleException{
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("USER_ID");
		if(id==null){
			throw new HandleException(ErrorCode.SESSION_ERROR, "登录已过期,请重新登录");
		}
		return id;
	}
	
	public static void setStoreId(HttpServletRequest request, Integer id){
		HttpSession session = request.getSession();
		session.setAttribute("STORE_ID", id);
		session.setMaxInactiveInterval(7200);
	}
	
	public static Integer getStoreId(HttpServletRequest request) throws HandleException{
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("STORE_ID");
		if(id==null){
			throw new HandleException(ErrorCode.SESSION_ERROR, "登录已过期,请重新登录");
		}
		return id;
	}

	public static void setSellerId(HttpServletRequest request, Integer id) {
		HttpSession session = request.getSession();
		session.setAttribute("SELLER_ID", id);
		session.setMaxInactiveInterval(7200);
	}
	
	public static Integer getSellerId(HttpServletRequest request) throws HandleException {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("SELLER_ID");
		if(id==null){
			throw new HandleException(ErrorCode.SESSION_ERROR, "登录已过期,请重新登录");
		}
		return id;
	}

	public static void setManagerId(HttpServletRequest request, Integer id) {
		HttpSession session = request.getSession();
		session.setAttribute("MANAGER_ID", id);
		session.setMaxInactiveInterval(7200);
	}
	
	public static Integer getManagerId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Integer id = (Integer) session.getAttribute("MANAGER_ID");
		if(id==null){
			throw new HandleException(ErrorCode.SESSION_ERROR, "登录已过期,请重新登录");
		}
		return id;
	}
}
