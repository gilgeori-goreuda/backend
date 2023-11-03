package com.pd.gilgeorigoreuda.member.exception;

import org.springframework.http.HttpStatus;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;

public class NoSuchMemberException extends GilgeorigoreudaException {
	public NoSuchMemberException() {
		super(HttpStatus.NOT_FOUND);
	}
}
