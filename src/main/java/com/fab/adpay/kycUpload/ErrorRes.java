package com.fab.adpay.kycUpload;

public class ErrorRes {
	private Integer errorCode;
	private String errorMsg;

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "{" + "errorCode=" + errorCode + ", errorDesc='" + errorMsg + '\'' + '}';
	}
}
