package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class DuplicatedVisitRecordException extends GilgeoriGoreudaException {
    public DuplicatedVisitRecordException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
