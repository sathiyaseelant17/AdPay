package com.fab.adpay.transactionHistory;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TransactionHistoryResponse {
	private int errorCode;
	private String errorText;
	private List<TransactionHistory> transactionHistory;
}
