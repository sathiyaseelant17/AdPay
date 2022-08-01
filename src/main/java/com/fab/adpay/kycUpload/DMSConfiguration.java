package com.fab.adpay.kycUpload;

public class DMSConfiguration {
	private DirectoryDetails directoryDetails;
	private DocumentDetails documentDetails;
	private String documentType;
	private String errorCode;
	private String errorText;
	private String fileName;
	private String fileType;
	private String objectFolder;
	private String objectPath;
	private String requestId;
	private String sourceSystemName;
	private String targetPathToUpload;

	public DirectoryDetails getDirDetails() {
		return directoryDetails;
	}

	public DocumentDetails getDocDetails() {
		return documentDetails;
	}

	public String getDocumentType() {
		return documentType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorText() {
		return errorText;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public String getObjectFolder() {
		return objectFolder;
	}

	public String getObjectPath() {
		return objectPath;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getSourceSystemName() {
		return sourceSystemName;
	}

	public String getTargetPathToUpload() {
		return targetPathToUpload;
	}

	public void setDirDetails(DirectoryDetails directoryDetails) {
		this.directoryDetails = directoryDetails;
	}

	public void setDocDetails(DocumentDetails documentDetails) {
		this.documentDetails = documentDetails;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setObjectFolder(String objectFolder) {
		this.objectFolder = objectFolder;
	}

	public void setObjectPath(String objectPath) {
		this.objectPath = objectPath;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setSourceSystemName(String sourceSystemName) {
		this.sourceSystemName = sourceSystemName;
	}

	public void setTargetPathToUpload(String targetPathToUpload) {
		this.targetPathToUpload = targetPathToUpload;
	}

}
