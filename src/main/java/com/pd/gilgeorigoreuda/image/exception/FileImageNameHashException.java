package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class FileImageNameHashException extends GilgeorigoreudaException {
    public FileImageNameHashException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
