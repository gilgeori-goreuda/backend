package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidImageUrlException extends GilgeoriGoreudaException {
    public InvalidImageUrlException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
