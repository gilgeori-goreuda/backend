package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class AlreadyExistInBoundaryException extends GilgeoriGoreudaException {
	public AlreadyExistInBoundaryException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
