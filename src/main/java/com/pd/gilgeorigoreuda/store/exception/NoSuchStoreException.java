package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class NoSuchStoreException extends GilgeorigoreudaException {
	public NoSuchStoreException() {
		super(HttpStatus.NOT_FOUND);
	}
}
