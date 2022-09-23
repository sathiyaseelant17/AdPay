package com.fab.adpay.walletInquiryFss;

import com.fab.adpay.walletInquiry.PreApproved;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletInquiryDataFss {
    private String walletId;
    private String walletLabel;
    private String defaultWallet;
    private String walletType;
    private String walletStatus;
    private String firstNameEnglish;
    private String middleNameEnglish;
    private String lastNameEnglish;
    private String firstNameArabic;
    private String middleNameArabic;
    private String lastNameArabic;
    private String mobile;
    private String email;
    private BigDecimal customerLimit;
    private String preferredLanguage;
    private String createDate;
    private String lastTransactionDate;
    private String lastTopupAmount;
    private String lastTopupDate;
    private BigDecimal walletSpendLimitPerTransaction;
    private BigDecimal availableBalance;
    private BigDecimal currentBalance;
    private List<PreApprovedFss> preApproved=new ArrayList<PreApprovedFss>();
    private String kycFlag;
}
