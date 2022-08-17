package com.fab.adpay.kycUpload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DMSConfiguration {
	private DirectoryDetails directoryDetails;
	private DocumentDetails documentDetails;
	private String documentType;
	private String statusCode;
	private String statusText;
	private String fileName;
	private String fileType;
	private String objectFolder;
	private String objectPath;
	private String requestId;
	private String sourceSystemName;
	private String targetPathToUpload;

}
