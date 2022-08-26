package com.fab.adpay.addWallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AddWalletRequest {

    private String defaultWalletId;
    private String walletLabel;
    private BigDecimal walletLimit;
    private BigDecimal walletSpendLimitPerTransaction;


}
