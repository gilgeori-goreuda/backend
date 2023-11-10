package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class NoRepoterMemberException extends GilgeorigoreudaException {
	public NoRepoterMemberException() {
		super(HttpStatus.UNAUTHORIZED);
	}
}
