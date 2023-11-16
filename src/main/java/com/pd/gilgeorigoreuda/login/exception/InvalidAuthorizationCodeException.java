package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidAuthorizationCodeException extends GilgeorigoreudaException {
    public InvalidAuthorizationCodeException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
