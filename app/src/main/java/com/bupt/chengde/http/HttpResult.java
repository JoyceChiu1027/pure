package com.bupt.chengde.http;

public class HttpResult<T> {

	int error_code;
	String reason;
	T result;

	public int getCode() {
		return error_code;
	}

	public void setCode(int code) {
		this.error_code = code;
	}

	public String getMess() {
		return reason;
	}

	public void setMess(String mess) {
		this.reason = mess;
	}

	public T getData() {
		return result;
	}

	public void setData(T data) {
		this.result = data;
	}

}
