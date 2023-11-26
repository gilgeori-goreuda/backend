package com.pd.gilgeorigoreuda.review.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewPreferenceType {

	LIKE("좋아요"),
	NONE("무관"),
	HATE("싫어요");

	private final String value;

}
