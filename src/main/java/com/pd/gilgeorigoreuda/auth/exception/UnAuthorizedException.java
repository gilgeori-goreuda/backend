package com.pd.gilgeorigoreuda.auth.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class UnAuthorizedException extends GilgeorigoreudaException {
    public UnAuthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
