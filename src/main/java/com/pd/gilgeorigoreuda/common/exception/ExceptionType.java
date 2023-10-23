package com.pd.gilgeorigoreuda.common.exception;

import java.util.Arrays;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

	UNKNOWN_EXCEPTION("X001", "알 수 없는 서버 에러입니다."),
	;

	private final String errorCode;
	private final String errorMessage;
	private Class<? extends GilgeorigoreudaException> classType;

	ExceptionType(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public static ExceptionType of(Class<?> classType) {
		return Arrays.stream(values())
			.filter(exceptionType -> Objects.nonNull(exceptionType.classType) && exceptionType.classType.equals(classType))
			.findFirst()
			.orElse(UNKNOWN_EXCEPTION);
	}

}
