package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReporterResponse {

	private Long memberId;
	private String memberNickname;

	public ReporterResponse(final Long memberId, final String memberNickname) {
		this.memberId = memberId;
		this.memberNickname = memberNickname;
	}

}
