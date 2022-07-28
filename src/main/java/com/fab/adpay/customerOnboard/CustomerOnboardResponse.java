/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import com.fab.adpay.customerOnboard.model.ResponseData;
import com.fab.adpay.customerOnboard.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerOnboardResponse {
    private String requestID;
    private String requestTimeStamp;
    private String channelID;
    private ResponseStatus responseStatus;
    private String websitePageIdentifier;
    private ResponseData responseData;
}
