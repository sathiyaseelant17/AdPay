package com.fab.adpay.walletInquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    private int lastTransactionDate;
    private String lastTopupAmount;
    private String lastTopupDate;
    private BigDecimal walletLimit;
    private BigDecimal walletSpendLimitPerTransaction;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private String adgeId;
    private String serviceId;
    private String kycFlag;

}
