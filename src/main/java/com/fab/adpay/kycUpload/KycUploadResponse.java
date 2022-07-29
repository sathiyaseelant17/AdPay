package com.fab.adpay.kycUpload;

public class KycUploadResponse {
	private String documentId;
	private String errorCode;
	private String errorText;

	public String getDocumentId() {
		return documentId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
}
