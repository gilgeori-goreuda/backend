package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class TooLongDistanceException extends GilgeorigoreudaException {
        public TooLongDistanceException() {
            super(HttpStatus.BAD_REQUEST);
        }
}
