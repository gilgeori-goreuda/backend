package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidAccessTokenException extends GilgeoriGoreudaException {
    public InvalidAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
