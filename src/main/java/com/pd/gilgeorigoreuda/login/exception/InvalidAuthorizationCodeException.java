package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidAuthorizationCodeException extends GilgeoriGoreudaException {
    public InvalidAuthorizationCodeException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
