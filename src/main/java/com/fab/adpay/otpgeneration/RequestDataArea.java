package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDataArea {

	private String mobileNumber;
	private String emailID;
	private String transactionCode;
	private Integer digitsInOTP;
	private String splitOTP;
	private Integer expiryTime;
	private String alertType;
	private Integer maxFailedAttempt;

}
