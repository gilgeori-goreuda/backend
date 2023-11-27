package com.pd.gilgeorigoreuda.review.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class NoSuchReviewException extends GilgeoriGoreudaException {
    public NoSuchReviewException() {
        super(HttpStatus.NOT_FOUND);
    }
}
