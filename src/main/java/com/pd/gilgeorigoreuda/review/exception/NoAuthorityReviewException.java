package com.pd.gilgeorigoreuda.review.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NoAuthorityReviewException extends GilgeoriGoreudaException {
    public NoAuthorityReviewException() {
        super(HttpStatus.FORBIDDEN);
    }
}
