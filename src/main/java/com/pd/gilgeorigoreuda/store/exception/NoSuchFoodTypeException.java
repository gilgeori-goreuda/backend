package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class NoSuchFoodTypeException extends GilgeoriGoreudaException {
	public NoSuchFoodTypeException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
