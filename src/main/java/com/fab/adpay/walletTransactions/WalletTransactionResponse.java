package com.fab.adpay.walletTransactions;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class WalletTransactionResponse {

    private String cardId;
    private String errorText;
    private int errorCode;
    private BigDecimal avlBalAmount;
    private BigDecimal curBalAmount;
    private String reqRspTime;
    private String trackExpiryDate;
}
