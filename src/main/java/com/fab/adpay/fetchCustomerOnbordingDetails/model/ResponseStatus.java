package com.fab.adpay.fetchCustomerOnbordingDetails.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatus {
    private String status;
    private String errorDescription;

}
