package com.fab.adpay.otpvalidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpValidationResponse {

	private Boolean success;
	private String errorCode;
	private String errorText;
//	private String cardId;

}
