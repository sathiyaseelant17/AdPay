/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.customerOnboard;

import com.fab.adpay.customerOnboard.model.RequestData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOnboardRequest {
    private String requestId;
    private String requestTimeStamp;
    private String channelId;
    private RequestData requestData;

}
