package com.fab.adpay.otpvalidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpValidationApiResponse {
    private ApplicationArea applicationArea;
    private OtpValidationApiResponseStatus responseStatus;
    private OtpValidationApiDataArea dataArea;
}
