package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidImageUrlException extends GilgeorigoreudaException {
    public InvalidImageUrlException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
