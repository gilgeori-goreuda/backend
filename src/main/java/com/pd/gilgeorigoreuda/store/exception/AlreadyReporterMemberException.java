package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class AlreadyReporterMemberException extends GilgeorigoreudaException {
	public AlreadyReporterMemberException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
