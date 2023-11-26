package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class InvalidRefreshTokenException extends GilgeoriGoreudaException {
    public InvalidRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
