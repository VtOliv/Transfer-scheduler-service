package com.bank.transfers.exception;

import org.springframework.http.HttpStatus;

public class FeeNotFoundException extends BusinessException {
	private static final long serialVersionUID = -4077623339671616919L;
	
	public FeeNotFoundException() {
		super.setHttpStatusCode(HttpStatus.NOT_FOUND);
		super.setMessage("Não há taxa aplicável para a data informada. Transferência não permitida.");
	}
}
