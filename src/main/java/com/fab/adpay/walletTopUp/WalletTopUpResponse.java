package com.fab.adpay.walletTopUp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WalletTopUpResponse {
    private String walletId;
    private String trackExpiryDate;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private String activationCode;
    private String cardCurrentCode;
    private int transactionId;
    private String expiryDate;
    private String creditAc;
    private String creditAcPosId;
    private String statusText;
    private int statusCode;
}
