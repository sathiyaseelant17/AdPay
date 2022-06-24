/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fab.adpay.preApproval;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PreApprovalRequest {

    private String cardId;
    private String adgeId;
    private String serviceId;
    private int requestType;
}
