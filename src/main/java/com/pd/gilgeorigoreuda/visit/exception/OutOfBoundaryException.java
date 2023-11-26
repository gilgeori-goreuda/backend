package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class OutOfBoundaryException extends GilgeoriGoreudaException {
    public OutOfBoundaryException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
