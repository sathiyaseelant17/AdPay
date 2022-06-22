package com.fab.adpay.transactionHistory;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TransactionHistoryResponse {
	private BigDecimal billedAmount;
	private String billedCurrencyCode;
	private String creditDebitFlag;
	private BigDecimal currentBalance;
	private String desc1;
	private String desc2;
	private String errorCode;
	private String errorText;
	private String mcc;
	private BigDecimal transactionAmount;
	private String transactionCurrencyCode;
	private BigDecimal transactionRate;
	private String transactionReferenceNumber;
	private String transactionSourceDesc;
	private String transactionTypeDesc;
	private String txnDateTime;
}
