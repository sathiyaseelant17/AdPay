package com.fab.adpay.kycUpload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentDetails {
	private String aclCardId;
	private String aclDomain;
	private String aclName;
	private String documentType;
	private String elpasaCif;
	private String emiratesId;
	private String referenceNumber;
	private String objectName;
	private String product;


}
