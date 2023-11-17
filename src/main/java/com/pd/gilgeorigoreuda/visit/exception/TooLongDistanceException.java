package com.pd.gilgeorigoreuda.visit.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class TooLongDistanceException extends GilgeoriGoreudaException {
        public TooLongDistanceException() {
            super(HttpStatus.BAD_REQUEST);
        }
}
