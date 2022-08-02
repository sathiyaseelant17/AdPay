package com.fab.adpay.walletTransactions;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class WalletTransactionResponse {

    private String walletId;
    private String statusText;
    private int statusCode;
    private BigDecimal avlBalAmount;
    private BigDecimal curBalAmount;
    private String reqRspTime;
    private String trackExpiryDate;
}
