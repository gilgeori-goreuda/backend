package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class ExpiredPeriodRefreshTokenException extends GilgeoriGoreudaException {
    public ExpiredPeriodRefreshTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
