package com.fab.adpay.otpgeneration;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOtpServiceRequest {

	private ApplicationArea applicationArea;
	private RequestDataArea dataArea;

}
