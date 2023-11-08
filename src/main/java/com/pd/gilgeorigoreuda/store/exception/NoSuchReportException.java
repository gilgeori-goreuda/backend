package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchReportException extends GilgeorigoreudaException {
	public NoSuchReportException() {
		super(HttpStatus.NOT_FOUND);
	}
}
