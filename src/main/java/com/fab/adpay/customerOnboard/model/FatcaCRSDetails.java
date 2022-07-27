/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FatcaCRSDetails {

    private String employerName;
    private Float monthlyIncome;
    private String trdLicensePlaceOfIssue;
    private String passportNumber;
    private String dualNationality;
    private String alternateNationality;
    private String employmentStatus;
    private List<String> industries;
    private String avgTransactionValue;
    private String declaration;
    private String reasonForNoDeclaration;
    private String TINNumber;
    private String greenCardID;
    private String USPerson;
    private String CRSDeclaration;
    private String nonUAENonUSTaxResident;
    private List<TaxResidency> taxResidency;
    private String residentInOtherJurisdiction;
    private List<Jurisdiction> jurisdiction;
    private List<String> personalTaxJurisdiction;
    private String UAEResidencyByRBIScheme;
}
