package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchStoreReportHistoryException extends GilgeorigoreudaException {
	public NoSuchStoreReportHistoryException() {
		super(HttpStatus.NOT_FOUND);
	}
}
