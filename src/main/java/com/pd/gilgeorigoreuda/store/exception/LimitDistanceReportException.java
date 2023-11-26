package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class LimitDistanceReportException extends GilgeoriGoreudaException {
	public LimitDistanceReportException() {
		super(HttpStatus.BAD_REQUEST);
	}
}
