package com.fab.adpay.updateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateCustomerRequest {
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String addressLine4;
    private String birthPlace;
    private String cardId;
    private String city;
    private String companyName;
    private String countryCode;
    private String countryOfResidence;
    private String dob;
    private String email;
    private String embossedName;
    private int emirates;
    private String firstName;
    private String gender;
    private String iban;
    private String idExpiryDate;
    private String idIssueCountryId;
    private String idNumber;
    private int idType;
    private String lastName;
    private String middleName;
    private String mobile;
    private String nationality;
    private String occupation;
    private String postalCode;
    private int prefixTitle;
    private String visaExpiryDate;
}
