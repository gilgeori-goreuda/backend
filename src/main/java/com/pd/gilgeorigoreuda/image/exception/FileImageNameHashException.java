package com.pd.gilgeorigoreuda.image.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class FileImageNameHashException extends GilgeoriGoreudaException {
    public FileImageNameHashException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
