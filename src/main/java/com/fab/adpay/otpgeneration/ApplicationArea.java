package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationArea {

	private String correlationId;
	private String interfaceID;
	private String countryOfOrigin;
	private String senderId;
	private String senderUserId;
	private String senderBranchId;
	private String senderAuthorizationId;
	private String senderReferenceId;
	private String transactionId;
	private String transactionDateTime;
	private String transactionTimeZone;
	private String senderAuthorizationComments;
	private String language;
	private String creationDateTime;
	private String requiredExecutionDate;



}