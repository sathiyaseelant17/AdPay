/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletStatusUpdate;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class  WalletStatusUpdateRequest {

	private String cardId;
	private int changeDate;
	private int surrenderFlag;
	private String replacedFlag;
	private int passCodeStatus;
	private int passCodeCount;
	private int registrationStatus;
	private String pinActive;
	private String expiryDate;
	private String trackExpiryDate;
	private String dormantFlag;
	private int cardSaleStatus;
	private String newTrackExpiryDate;
	private int newCardStatus;
	private int reasonKey;
	private String makerId;
	private String makerDt;
	private String authorizationId;
	private int cardUsed;
	private String cardRenewalFlag;
	private int riskRating;
	private String reCardFlag;
	private String firstActFlag;
	private int transactionSource;
	private int transactionType;
	private String posId;
	private int audItTrail;
	private String desc2;
	private String sourceTransactionReference;
	private String smsFlag;
	private String autoCardRenewalFlag;
	private int newStatus;
	private String reasonText;

}
