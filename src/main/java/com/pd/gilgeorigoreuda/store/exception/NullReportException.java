package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NullReportException extends GilgeoriGoreudaException {
	public NullReportException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
