package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NotVerifiedVisitRecordException extends GilgeoriGoreudaException {
    public NotVerifiedVisitRecordException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
