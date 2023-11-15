package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class ExpiredPeriodRefreshTokenException extends GilgeorigoreudaException {
    public ExpiredPeriodRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
