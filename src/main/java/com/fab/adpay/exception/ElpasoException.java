package com.fab.adpay.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElpasoException extends RuntimeException {
	private Integer statusCode;
	private String statusText;
	private String transactionId;
}
