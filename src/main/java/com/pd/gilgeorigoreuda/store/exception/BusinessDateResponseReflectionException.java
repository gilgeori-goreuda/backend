package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class BusinessDateResponseReflectionException extends GilgeoriGoreudaException {
	public BusinessDateResponseReflectionException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
