package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NoRepoterMemberException extends GilgeoriGoreudaException {
	public NoRepoterMemberException() {
		super(HttpStatus.UNAUTHORIZED);
	}
}
