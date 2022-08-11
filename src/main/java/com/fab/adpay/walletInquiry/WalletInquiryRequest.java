/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.walletInquiry;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WalletInquiryRequest {

    private int identityType;
    private String identityNumber;
    private String walletId;

}
