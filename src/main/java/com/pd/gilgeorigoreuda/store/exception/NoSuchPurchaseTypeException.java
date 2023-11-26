package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class NoSuchPurchaseTypeException extends GilgeoriGoreudaException {
	public NoSuchPurchaseTypeException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
