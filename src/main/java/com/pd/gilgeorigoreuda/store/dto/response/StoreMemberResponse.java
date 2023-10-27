package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreMemberResponse {

	private Long memberId;
	private String memberNickname;

	public StoreMemberResponse(final Long memberId, final String memberNickname) {
		this.memberId = memberId;
		this.memberNickname = memberNickname;
	}

}
