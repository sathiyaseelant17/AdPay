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
    private String mobile;
    private String email;
    private String walletStatus;
    private String createDate;
    private String lastTransaxtionDate;
    private String lastTopupAmountDate;
    private String walletTopupLimit;
    private String availableBalance;
    private String currentBalance;
    private String adgeId;
    private String serviceId;
    
    
}
