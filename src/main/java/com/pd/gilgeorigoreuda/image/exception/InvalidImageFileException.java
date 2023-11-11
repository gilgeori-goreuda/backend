package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidImageFileException extends GilgeorigoreudaException {
    public InvalidImageFileException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
