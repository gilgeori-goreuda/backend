package com.pd.gilgeorigoreuda.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pd.gilgeorigoreuda.common.exception.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

	private static final String INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT = "%s : %s (request value: %s)";

	@ExceptionHandler(GilgeorigoreudaException.class)
	public ResponseEntity<ErrorResponse> gilgeorigoreudaException(GilgeorigoreudaException e) {
		return ResponseEntity.status(e.getStatus())
			.body(new ErrorResponse(e.getErrorCode(), e.getErrorMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> unknownException(Exception e) {
		StackTraceElement[] stackTrace = e.getStackTrace();

		log.error(String.format("Unknown Exception !! : %s\n" + "%s:%s:%s", e, stackTrace[0].getClassName(),
			stackTrace[0].getMethodName(), stackTrace[0].getLineNumber()), e);

		String errorCode = ExceptionType.UNKNOWN_EXCEPTION.getErrorCode();
		String message = ExceptionType.UNKNOWN_EXCEPTION.getErrorMessage();

		return ResponseEntity.internalServerError()
			.body(new ErrorResponse(errorCode, message));
	}

}
