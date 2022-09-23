package com.fab.adpay.walletInquiryFss;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletInquiryFssRequest {

    private int identityType;
    private String identityNumber;
    private String walletId;

}
