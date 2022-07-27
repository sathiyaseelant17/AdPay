/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private List<String> documentDetails;
    private String actionName;
    private  CustomerDetails customerDetails;
    private FatcaCRSDetails fatcaCRSDetails;
}
