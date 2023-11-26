package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class NoSuchStoreTypeException extends GilgeoriGoreudaException {
	public NoSuchStoreTypeException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
