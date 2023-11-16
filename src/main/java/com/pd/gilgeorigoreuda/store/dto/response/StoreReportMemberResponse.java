package com.pd.gilgeorigoreuda.store.dto.response;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreReportMemberResponse {

	private Long id;
	private String nickname;
	private String profileImageUrl;

	public StoreReportMemberResponse(
			final Long id,
			final String nickname,
			final String profileImageUrl) {
		this.id = id;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
	}

	public static StoreReportMemberResponse of(final Member member) {
		return new StoreReportMemberResponse(
				member.getId(),
				member.getNickname(),
				member.getProfileImageUrl()
		);
	}

}
