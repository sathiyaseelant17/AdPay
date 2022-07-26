package com.fab.adpay.otpvalidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationApiResponseStatus {
    private String status;
    private List<ErrorDetail> errorDetails;

}
