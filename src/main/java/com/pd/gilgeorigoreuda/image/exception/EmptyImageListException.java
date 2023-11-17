package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class EmptyImageListException extends GilgeoriGoreudaException {
    public EmptyImageListException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
