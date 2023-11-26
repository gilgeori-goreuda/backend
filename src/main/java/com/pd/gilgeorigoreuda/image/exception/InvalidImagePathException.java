package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidImagePathException extends GilgeoriGoreudaException {
    public InvalidImagePathException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
