package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class RenewalAccessTokenFailException extends GilgeoriGoreudaException {
    public RenewalAccessTokenFailException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
