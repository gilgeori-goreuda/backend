package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidImagePathException extends GilgeorigoreudaException {
    public InvalidImagePathException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
