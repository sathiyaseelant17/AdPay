package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationArea {
	private String countryOfOrigin;
	private String senderId;
	private String transactionDateTime;
	private String transactionId;
	private String language;

}