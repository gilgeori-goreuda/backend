package com.pd.gilgeorigoreuda.common.exception;

import java.util.Arrays;
import java.util.Objects;

import com.pd.gilgeorigoreuda.auth.exception.EmailDuplicatedException;
import com.pd.gilgeorigoreuda.image.exception.*;
import com.pd.gilgeorigoreuda.store.exception.*;
import com.pd.gilgeorigoreuda.visit.exception.NoSuchStoreVisitRecordException;
import com.pd.gilgeorigoreuda.visit.exception.OutOfBoundaryException;
import com.pd.gilgeorigoreuda.visit.exception.TimeOutException;
import com.pd.gilgeorigoreuda.visit.exception.TooLongDistanceException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionType {

	UNKNOWN_EXCEPTION("X001", "알 수 없는 서버 에러입니다."),

	EMAIL_DUPLICATED_EXCEPTION("M001", "중복된 이메일 입니다.", EmailDuplicatedException.class),
	INVALID_DATE_TIME_FORMAT("X002", "유효하지 않은 날짜 형식입니다. (00:00-23:59)"),

	NO_SUCH_STORE_EXCEPTION("S001", "존재하지 않는 가게입니다.", NoSuchStoreException.class),
	NO_SUCH_STORE_TYPE_EXCEPTION("S002", "존재하지 않는 가게 타입입니다.", NoSuchStoreTypeException.class),
	NO_SUCH_PURCHASE_TYPE_EXCEPTION("S003", "존재하지 않는 결제 타입입니다.", NoSuchPurchaseTypeException.class),
	NO_SUCH_FOOD_TYPE_EXCEPTION("S004", "존재하지 않는 음식 타입입니다.", NoSuchFoodTypeException.class),
	NO_OWNER_MEMBER_EXCEPTION("S005", "해당 가게의 제보자가 아닙니다.", NoOwnerMemberException.class),
	NO_REPORTER_MEMBER_EXCEPTION("S006", "해당 가게의 신고자가 아닙니다.", NoRepoterMemberException.class),
	ALREADY_EXIST_IN_BOUNDARY_EXCEPTION("S006", "해당 위치에 이미 존재하는 가게가 있습니다.", AlreadyExistInBoundaryException.class),
	ALREADY_REPORTER_MEMBER_EXCEPTION("S007", "이미 신고한 가게입니다.", AlreadyReporterMemberException.class),
	NO_SUCH_REPORT_EXCEPTION("S008", "존재하지 않는 신고입니다.", NoSuchReportException.class),
	OUT_OF_BOUNDARY_EXCEPTION("S007", "인증 가능 범위가 아닙니다.", OutOfBoundaryException.class),
	NO_SUCH_STORE_VISIT_RECORD_EXCEPTION("S008", "존재하지 않는 가게 방문 기록입니다.", NoSuchStoreVisitRecordException.class),
	TIME_OUT_EXCEPTION("S009", "인증 시간이 초과되었습니다. 2시간이 지난 방문 기록입니다.", TimeOutException.class),
	TOO_LONG_DISTANCE_EXCEPTION("S010", "방문하기에 너무 먼 거리에 있습니다. 근처에 가서 다시 시도해주세요.", TooLongDistanceException.class),
	LIMIT_DISTANCE_REPORT_EXCEPTION("S011", "신고하려는 가게와 사용자의 위치가 100m를 초과했습니다.", LimitDistanceReportException.class),

	IMAGE_NULL_EXCEPTION("I001", "업로드할 이미지 파일이 없습니다.", ImageNullException.class),
	INVALID_IMAGE_PATH_EXCEPTION("I002", "이미지를 저장할 경로가 올바르지 않습니다.", InvalidImagePathException.class),
	INVALID_IMAGE_URL_EXCEPTION("I003", "이미지 URL이 올바르지 않습니다.", InvalidImageUrlException.class),
	INVALID_IMAGE_FILE_EXCEPTION("I004", "이미지 파일이 아닙니다.", InvalidImageFileException.class),
	FILE_IMAGE_NAME_HASH_EXCEPTION("I005", "파일 이름을 해싱하는데 실패했습니다.", FileImageNameHashException.class),
	EXCEED_IMAGE_CAPACITY_EXCEPTION("I006", "업로드 가능한 이미지 크기를 초과했습니다.", ExceedImageCapacityException.class),
	EXCEED_IMAGE_LIST_SIZE_EXCEPTION("I006", "업로드 가능한 이미지 개수를 초과했습니다.", ExceedImageListSizeException.class),
	EMPTY_IMAGE_LIST_EXCEPTION("I007", "최소 한 장 이상의 이미지를 업로드해야합니다.", EmptyImageListException.class),
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
