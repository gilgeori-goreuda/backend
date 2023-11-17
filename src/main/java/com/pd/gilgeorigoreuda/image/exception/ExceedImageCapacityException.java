package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class ExceedImageCapacityException extends GilgeoriGoreudaException {
    public ExceedImageCapacityException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
