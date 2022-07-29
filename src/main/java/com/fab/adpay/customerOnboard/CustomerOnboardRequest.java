/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import com.fab.adpay.customerOnboard.model.Jurisdiction;
import com.fab.adpay.customerOnboard.model.RequestData;
import com.fab.adpay.customerOnboard.model.TaxResidency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOnboardRequest {

    private String applicationRefNo;
    private String applicationStatus;
    private String applicationRemarks;
    private String product;
    private String UAEPassResult;
    private String TandCAgreed;
    private String websitePageIdentifier;
    private String documentDetails;
    private String actionName;
    private String customerID;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String postalCode;
    private String city;
    private int emirate;
    private String emirateDesc;
    private String countryOfResidence;
    private String mobileNumber;
    private String emailID;
    private int idType;
    private String emiratesID;
    private String idIssueDate;
    private String idExpiryDate;
    private String idIssuePlace;
    private String idIssueCountry;
    private String embossedName;
    private String dateOfBirth;
    private String placeOfBirth;
    private String countryOfBirth;
    private String nationality;
    private String preferredLanguage;
    private int preferredBranchCode;
    private String preferredBranchName;
    private String occupation;
    private String companyName;
    private String companyEmbName;
    private String companyRegNo;
    private String dateOfEstablishment;
    private String telephoneNo;
    private float RiskRating;
    private String RiskRatingAMLSegment;
    private String PEPStatus;
    private String T24Company;
    private String T24CompanyCode;
    private String T24Sector;
    private String T24Target;
    private String T24AccountOfficer;
    private String T24Industry;
    private String HoldMailService;
    private String CashTransactionMode;
    private String employerName;
    private Float monthlyIncome;
    private String trdLicensePlaceOfIssue;
    private String passportNumber;
    private String dualNationality;
    private String alternateNationality;
    private String employmentStatus;
    private String industries;
    private String avgTransactionValue;
    private String declaration;
    private String reasonForNoDeclaration;
    private String TINNumber;
    private String greenCardID;
    private String USPerson;
    private String CRSDeclaration;
    private String nonUAENonUSTaxResident;
    private String taxResidency;
    private String residentInOtherJurisdiction;
    private String jurisdiction;
    private String personalTaxJurisdiction;
    private String UAEResidencyByRBIScheme;


}
