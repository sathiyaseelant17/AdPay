package com.fab.adpay.kycUpload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KycUploadRequest {
	private String walletId;
	private String content;
	private String documentType;

}
