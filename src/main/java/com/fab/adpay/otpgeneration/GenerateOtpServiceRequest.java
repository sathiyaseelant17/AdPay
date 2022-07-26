package com.fab.adpay.otpgeneration;

import com.magnati.authenticationservices.commons.model.ApplicationArea;
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
