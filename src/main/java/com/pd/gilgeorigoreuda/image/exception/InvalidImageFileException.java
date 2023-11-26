package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidImageFileException extends GilgeoriGoreudaException {
    public InvalidImageFileException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
