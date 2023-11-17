package com.pd.gilgeorigoreuda.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class GilgeoriGoreudaException extends RuntimeException {

	private final HttpStatus status;
	private final String errorCode;
	private final String errorMessage;

	public GilgeoriGoreudaException(HttpStatus status) {
		ExceptionType exceptionType = ExceptionType.of(this.getClass());
		this.status = status;
		this.errorCode = exceptionType.getErrorCode();
		this.errorMessage = exceptionType.getErrorMessage();
	}

}
