package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class NullReportException extends GilgeorigoreudaException {
	public NullReportException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
