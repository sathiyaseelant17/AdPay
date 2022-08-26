package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpGenerationResponse {
	private String walletId;
	private String referenceNumber;
}
