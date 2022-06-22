package com.fab.adpay.transactionHistory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TransactionHistory {

    private BigDecimal billedAmount;
    private String billedCurrencyCode;
    private String creditDebitFlag;
    private BigDecimal currentBalance;
    private String desc1;
    private String desc2;
    private String mcc;
    private BigDecimal transactionAmount;
    private String transactionCurrencyCode;
    private String transactionReferenceNumber;
    private String transactionSourceDesc;
    private String transactionTypeDesc;
    private String txnDateTime;
}
