package com.pd.gilgeorigoreuda.auth.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeorigoreudaException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends GilgeorigoreudaException {
    public RefreshTokenNotFoundException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
