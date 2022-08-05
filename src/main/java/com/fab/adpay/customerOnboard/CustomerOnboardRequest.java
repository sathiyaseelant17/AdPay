
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
    private String uaePassResult;
    private String tandCAgreed;
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
    private String eidIssueDate;
    private String eidExpiryDate;
    private String eidIssuePlace;
    private String eidIssueCountry;
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
    private float riskRating;
    private String pepStatus;
    private String t24Company;
    private String t24CompanyCode;
    private String t24Sector;
    private String t24Target;
    private String t24AccountOfficer;
    private String t24Industry;
    private String t24AMLSegment;
    private String holdMailService;
    private String cashTransactionMode;
    private String employerName;
    private Float monthlyIncome;
    private String trdLicensePlaceOfIssue;
    private String passportNumber;
    private String dualNationality;
    private String alternateNationality;
    private String employmentStatus;
    private String declaration;
    private String reasonForNoDeclaration;
    private String tinNumber;
    private String greenCardID;
    private String usPerson;
    private String crsDeclaration;
    private String nonUAENonUSTaxResident;
    private String residentInOtherJurisdiction;
    private String uaeResidencyByRBIScheme;
    private List<String> personalTaxJurisdiction;
    private String averageTransactionValue;
    private List<String> industries;
    private String jurisdictionCountry;
    private String tIN;
    private String reasonNoTIN;
    private String reason;
    private String otherResidency;
    private String residenceFromDate;
    private String residenceToDate;
    private String cardID;

}
