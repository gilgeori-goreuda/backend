package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class LimitDistanceReportException extends GilgeorigoreudaException {
	public LimitDistanceReportException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
