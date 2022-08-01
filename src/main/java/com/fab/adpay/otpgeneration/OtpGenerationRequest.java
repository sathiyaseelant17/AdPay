package com.fab.adpay.otpgeneration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpGenerationRequest {
    private int requestMode;
    private String value;
}
