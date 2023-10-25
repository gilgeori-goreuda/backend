package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class BusinessDateResponseReflectionException extends GilgeorigoreudaException {
	public BusinessDateResponseReflectionException() {
		super(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
