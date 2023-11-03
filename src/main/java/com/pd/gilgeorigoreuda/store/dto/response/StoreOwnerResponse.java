package com.pd.gilgeorigoreuda.store.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreOwnerResponse {

	private Long id;
	private String nickname;

	public StoreOwnerResponse(final Long id, final String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public static StoreOwnerResponse of(final Member member) {
		return new StoreOwnerResponse(member.getId(), member.getNickname());
	}

}
