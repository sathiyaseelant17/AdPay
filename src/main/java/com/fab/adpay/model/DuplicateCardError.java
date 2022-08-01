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
	private String cardId;
	private String cardNumber;
	private String errorCode;
	private String errorText;

}
