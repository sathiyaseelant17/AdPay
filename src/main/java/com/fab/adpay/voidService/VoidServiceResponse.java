package com.fab.adpay.voidService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class VoidServiceResponse {
    private String cardId;
    private String errorText;
    private BigDecimal avlBalAmount;
    private BigDecimal curBalAmount;
    private String requestRspTime;
    private String errorCode;
}
