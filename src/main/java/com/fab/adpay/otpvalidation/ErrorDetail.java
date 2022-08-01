package com.fab.adpay.otpvalidation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private String errorCode;
    private String errorDesc;
}
