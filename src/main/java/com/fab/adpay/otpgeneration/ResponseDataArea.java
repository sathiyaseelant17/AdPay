package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataArea {
	
	private String referenceNumber;
	private String mobileNumber;
	private String emailID;
	
}