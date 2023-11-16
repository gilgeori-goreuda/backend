package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class RenewalAccessTokenFailException extends GilgeorigoreudaException {
    public RenewalAccessTokenFailException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
