package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchReportException extends GilgeoriGoreudaException {
	public NoSuchReportException() {
		super(HttpStatus.NOT_FOUND);
	}
}
