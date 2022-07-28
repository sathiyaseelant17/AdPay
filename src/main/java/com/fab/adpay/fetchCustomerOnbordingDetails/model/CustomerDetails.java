/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.fetchCustomerOnbordingDetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {
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
    private String countryCode;
    private String mobileNumber;
    private String emailID;
    private int idType;
    private String emiratesID;
    private String eidIssueDate;
    private String eidExpiryDate;
    private int eidIssuePlace;
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
}
