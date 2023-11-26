package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class NoSuchStoreException extends GilgeoriGoreudaException {
	public NoSuchStoreException() {
		super(HttpStatus.NOT_FOUND);
	}
}
