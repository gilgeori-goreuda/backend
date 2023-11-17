package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class ExceedImageListSizeException extends GilgeoriGoreudaException {
    public ExceedImageListSizeException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
