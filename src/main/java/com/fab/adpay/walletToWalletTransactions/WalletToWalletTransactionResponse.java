package com.fab.adpay.walletToWalletTransactions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletToWalletTransactionResponse {

    private String walletId_01;
    private String walletId_02;
    private BigDecimal availableBalance_01;
    private BigDecimal currentBalance_01;
    private BigDecimal availableBalance_02;
    private BigDecimal currentBalance_02;
    private int txnId;
    private String errorText;
    private int errorCode;


}
