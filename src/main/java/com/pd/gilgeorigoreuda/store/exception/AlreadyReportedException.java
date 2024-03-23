package com.pd.gilgeorigoreuda.store.exception;

import com.pd.gilgeorigoreuda.common.exception.GilgeoriGoreudaException;
import org.springframework.http.HttpStatus;

public class AlreadyReportedException extends GilgeoriGoreudaException {
    public AlreadyReportedException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
