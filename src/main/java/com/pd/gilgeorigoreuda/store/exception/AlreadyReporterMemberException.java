package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class AlreadyReporterMemberException extends GilgeoriGoreudaException {
	public AlreadyReporterMemberException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
