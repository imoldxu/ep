package com.ly.service.context;

public class InternalErrorMessage {

	public int code;

	public String msg;
	
	public InternalErrorMessage(){
	}
	
	public InternalErrorMessage(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
