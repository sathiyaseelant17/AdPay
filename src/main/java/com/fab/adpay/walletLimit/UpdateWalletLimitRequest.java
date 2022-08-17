/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletLimit;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class UpdateWalletLimitRequest {

    private String walletId;
    private String defaultWallet;
    private String walletLabel;
    private BigDecimal walletLimit;
    private BigDecimal walletSpendLimitPerTransaction;

}
