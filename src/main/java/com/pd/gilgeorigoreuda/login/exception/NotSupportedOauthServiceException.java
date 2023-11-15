package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class NotSupportedOauthServiceException extends GilgeorigoreudaException {
    public NotSupportedOauthServiceException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
