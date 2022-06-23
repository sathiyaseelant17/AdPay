package com.fab.cashee.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ElpasoException extends RuntimeException {
	private Integer errorCode;
	private String errorDesc;
	private String transactionId;
}
