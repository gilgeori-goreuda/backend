package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class NoOwnerMemberException extends GilgeorigoreudaException {
	public NoOwnerMemberException() {
		super(HttpStatus.UNAUTHORIZED);
	}
}
