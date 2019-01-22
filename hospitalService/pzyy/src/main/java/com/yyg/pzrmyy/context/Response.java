package com.yyg.pzrmyy.context;

public class Response {
	
	public static final int ERROR = 0;
    public static final int SUCCESS = 1;
    public static final String SUCCESS_MSG = "成功";
	
    
	private int code;

    private Object data;

    private String msg;

    public Response(int code, Object data, String msg) {
        this.code = code;
        this.setData(data);
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public Response setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Response setMsg(String msg) {
        this.msg = msg;
        return this;
    }

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
