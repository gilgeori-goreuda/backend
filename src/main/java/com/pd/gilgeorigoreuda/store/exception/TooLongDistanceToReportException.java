package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class TooLongDistanceToReportException extends GilgeoriGoreudaException {
	public TooLongDistanceToReportException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
