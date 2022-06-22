package com.fab.adpay.transactionHistory;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class TransactionHistoryRequest {

	private int requestType;
	@NotNull
	@NotEmpty
	private String cardId;
	@NotNull
	@NotEmpty
	private String startDate;
	@NotNull
	@NotEmpty
	private String endDate;
	private int numberOfTxns;

}
