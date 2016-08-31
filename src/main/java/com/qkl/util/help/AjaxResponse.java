package com.qkl.util.help;

import java.io.Serializable;

/**
 * 封装ajax返回
 * @author guowei
 *
 */
public class AjaxResponse implements Serializable {
	
	private static final long serialVersionUID = -7145191916102005908L;

	private Integer errorCode;
	
	private String message;
	
	private Object data;
	
	private Boolean success;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AjaxResponse [errorCode=" + errorCode + ", message=" + message
				+ ", data=" + data + ", success=" + success + "]";
	}
	
}
