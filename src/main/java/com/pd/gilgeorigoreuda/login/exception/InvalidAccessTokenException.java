package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends GilgeorigoreudaException {
    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
