package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class EmptyImageListException extends GilgeorigoreudaException {
    public EmptyImageListException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
