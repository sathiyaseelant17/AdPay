package com.fab.adpay.kycUpload;

public class DirectoryDetails {
	private String aclDomain;
	private String aclName;
	private String folderName;

	public DirectoryDetails() {
	}

	public DirectoryDetails(String folderName, String aclName, String aclDomain) {
		this.folderName = folderName;
		this.aclName = aclName;
		this.aclDomain = aclDomain;
	}

	public String getAclDomain() {
		return aclDomain;
	}

	public String getAclName() {
		return aclName;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setAclDomain(String aclDomain) {
		this.aclDomain = aclDomain;
	}

	public void setAclName(String aclName) {
		this.aclName = aclName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
}
