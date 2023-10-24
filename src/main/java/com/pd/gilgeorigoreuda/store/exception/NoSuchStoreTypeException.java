package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class NoSuchStoreTypeException extends GilgeorigoreudaException {
	public NoSuchStoreTypeException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
