package com.fab.adpay.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateCardError {
	private String walledId;
	private String cardNumber;
	private String statusCode;
	private String statusText;

}
