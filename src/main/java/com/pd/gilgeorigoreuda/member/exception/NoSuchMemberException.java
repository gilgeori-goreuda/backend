package com.pd.gilgeorigoreuda.member.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;

public class NoSuchMemberException extends GilgeoriGoreudaException {
	public NoSuchMemberException() {
		super(HttpStatus.NOT_FOUND);
	}
}
