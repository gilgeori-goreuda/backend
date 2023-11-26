package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class ImageNullException extends GilgeoriGoreudaException {
    public ImageNullException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
