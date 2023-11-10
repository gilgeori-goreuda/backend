package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class OutOfBoundaryException extends GilgeorigoreudaException {
    public OutOfBoundaryException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
