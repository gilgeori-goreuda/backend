package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class TimeOutException extends GilgeoriGoreudaException {
    public TimeOutException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
