package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class ExpiredPeriodAccessTokenException extends GilgeorigoreudaException {
    public ExpiredPeriodAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
