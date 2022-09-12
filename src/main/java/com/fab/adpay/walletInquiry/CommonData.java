package com.fab.adpay.walletInquiry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonData {
    private String firstNameEnglish;
    private String middleNameEnglish;
    private String lastNameEnglish;
    private String firstNameArabic;
    private String middleNameArabic;
    private String lastNameArabic;
    private String mobile;
    private String email;
    private String preferredLanguage;
    private BigDecimal customerLimit;
}
