package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateOtpServiceResponse {

	private ApplicationArea applicationArea;
	private ResponseStatus responseStatus;
	private ResponseDataArea dataArea;

}
