package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class TimeOutException extends GilgeorigoreudaException {
    public TimeOutException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
