package com.pd.gilgeorigoreuda.auth.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class RefreshTokenNotFoundException extends GilgeoriGoreudaException {
    public RefreshTokenNotFoundException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
