package com.fab.adpay.transactionHistory;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@ToString
public class TransactionHistoryRequest {

    private int requestType;
    private String cardId;
    private String startDate;
    private String endDate;
    private int numberOfTxns;
    private int requestMode;

}
