package com.bank.transfers.exception;

import org.springframework.http.HttpStatus;

public class InvallidAccountException extends BusinessException {
	private static final long serialVersionUID = -4077623339671616919L;
	
	public InvallidAccountException() {
		super.setHttpStatusCode(HttpStatus.BAD_REQUEST);
		super.setMessage("A conta de origem deve ser diferente da conta de destino.");
	}
}
