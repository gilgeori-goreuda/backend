package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class NoSuchPurchaseTypeException extends GilgeorigoreudaException {
	public NoSuchPurchaseTypeException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
