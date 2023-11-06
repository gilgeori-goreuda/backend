package com.pd.gilgeorigoreuda.common.exception;

import java.util.Arrays;
import java.util.Objects;

import com.pd.gilgeorigoreuda.store.exception.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

	UNKNOWN_EXCEPTION("X001", "알 수 없는 서버 에러입니다."),

	NO_SUCH_STORE_EXCEPTION("S001", "존재하지 않는 가게입니다.", NoSuchStoreException.class),
	NO_SUCH_STORE_TYPE_EXCEPTION("S002", "존재하지 않는 가게 타입입니다.", NoSuchStoreTypeException.class),
	NO_SUCH_PURCHASE_TYPE_EXCEPTION("S003", "존재하지 않는 결제 타입입니다.", NoSuchPurchaseTypeException.class),
	NO_SUCH_FOOD_TYPE_EXCEPTION("S004", "존재하지 않는 음식 타입입니다.", NoSuchFoodTypeException.class),
	NO_OWNER_MEMBER_EXCEPTION("S005", "해당 가게의 제보자가 아닙니다.", NoOwnerMemberException.class),
	ALREADY_EXIST_IN_BOUNDARY_EXCEPTION("S006", "해당 위치에 이미 존재하는 가게가 있습니다.", AlreadyExistInBoundaryException.class)
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
