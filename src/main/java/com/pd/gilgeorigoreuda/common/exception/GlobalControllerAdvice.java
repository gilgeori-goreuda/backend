package com.pd.gilgeorigoreuda.common.exception;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pd.gilgeorigoreuda.common.exception.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

	private static final String INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT = "%s : %s (request value: %s)";

	@ExceptionHandler(GilgeoriGoreudaException.class)
	public ResponseEntity<ErrorResponse> gilgeoriGoreudaException(GilgeoriGoreudaException e) {
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException e) {
		log.error(String.format("MethodArgumentNotValidException : %s", e));

		FieldError firstFieldError = e.getFieldErrors().get(0);
		String errorCode = firstFieldError.getCode();
		String errorMessage = String.format(INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT, firstFieldError.getField(),
				firstFieldError.getDefaultMessage(), firstFieldError.getRejectedValue());

		return ResponseEntity
				.badRequest()
				.body(new ErrorResponse(errorCode, errorMessage));
	}

	@ExceptionHandler(SizeLimitExceededException.class)
	public ResponseEntity<ErrorResponse> handleSizeLimitExceededException(final SizeLimitExceededException e) {
		log.warn(e.getMessage(), e);

		String errorCode = ExceptionType.EXCEED_IMAGE_CAPACITY_EXCEPTION.getErrorCode();
		String errorMessage = ExceptionType.EXCEED_IMAGE_CAPACITY_EXCEPTION.getErrorMessage()
				+ " 입력된 이미지 용량은 " + e.getActualSize() + " byte 입니다. "
				+ "(제한 용량: " + e.getPermittedSize() + " byte)";

		return ResponseEntity.badRequest()
				.body(new ErrorResponse(errorCode, errorMessage));
	}

}
