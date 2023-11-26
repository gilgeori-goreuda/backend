package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchStoreReportHistoryException extends GilgeoriGoreudaException {
	public NoSuchStoreReportHistoryException() {
		super(HttpStatus.NOT_FOUND);
	}
}
