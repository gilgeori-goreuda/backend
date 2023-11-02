package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class AlreadyExistInBoundaryException extends GilgeorigoreudaException {
	public AlreadyExistInBoundaryException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
