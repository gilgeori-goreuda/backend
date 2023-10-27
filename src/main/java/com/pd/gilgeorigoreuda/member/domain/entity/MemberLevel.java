package com.pd.gilgeorigoreuda.member.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberLevel {

	BEGINNER("길알못"),
	INTERMEDIATE("길좀알"),
	EXPERT("길잘알");

	private final String name;

}
