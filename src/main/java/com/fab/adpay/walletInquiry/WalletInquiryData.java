package com.fab.adpay.walletInquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletInquiryData {
    private String walletId;
    private String walletLabel;
    private String defaultWallet;
    private String walletType;
    private String firstNameEnglish;
    private String middleNameEnglish;
    private String lastNameEnglish;
    private String firstNameArabic;
    private String middleNameArabic;
    private String lastNameArabic;
}
