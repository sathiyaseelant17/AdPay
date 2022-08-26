package com.fab.adpay.otpgeneration;

import com.fab.adpay.customerOnboard.model.CustomerDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
	private String sendAsAttachement;
	private String templateName;
	private String customerIdentifier;
	private List<CustomerOTPDetails> identityDetails;
	private List<CustomerOTPDetails> templateAttributes;





}
