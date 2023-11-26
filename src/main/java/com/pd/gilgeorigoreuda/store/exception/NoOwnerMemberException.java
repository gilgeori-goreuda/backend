package com.pd.gilgeorigoreuda.store.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class NoOwnerMemberException extends GilgeoriGoreudaException {
	public NoOwnerMemberException() {
		super(HttpStatus.UNAUTHORIZED);
	}
}
