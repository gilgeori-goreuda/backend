package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchStoreVisitRecordException extends GilgeorigoreudaException {
    public NoSuchStoreVisitRecordException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
