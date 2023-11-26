package com.pd.gilgeorigoreuda.login.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NotSupportedOauthServiceException extends GilgeoriGoreudaException {
    public NotSupportedOauthServiceException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
