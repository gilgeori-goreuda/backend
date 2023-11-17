package com.pd.gilgeorigoreuda.auth.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends GilgeoriGoreudaException {
    public UnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
