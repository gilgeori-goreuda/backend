package com.pd.gilgeorigoreuda.auth.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class EmailDuplicatedException extends GilgeorigoreudaException {
    public EmailDuplicatedException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
