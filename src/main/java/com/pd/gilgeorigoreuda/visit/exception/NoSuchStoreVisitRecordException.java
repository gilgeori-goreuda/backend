package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchStoreVisitRecordException extends GilgeoriGoreudaException {
    public NoSuchStoreVisitRecordException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
