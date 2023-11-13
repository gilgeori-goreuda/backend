package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class ExceedImageCapacityException extends GilgeorigoreudaException {
    public ExceedImageCapacityException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
