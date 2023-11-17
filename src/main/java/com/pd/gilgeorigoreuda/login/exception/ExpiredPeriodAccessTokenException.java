package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class ExpiredPeriodAccessTokenException extends GilgeoriGoreudaException {
    public ExpiredPeriodAccessTokenException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
