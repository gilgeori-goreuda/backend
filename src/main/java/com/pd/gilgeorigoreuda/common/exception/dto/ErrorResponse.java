package com.pd.gilgeorigoreuda.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

	private final String errorCode;
	private final String message;

}
