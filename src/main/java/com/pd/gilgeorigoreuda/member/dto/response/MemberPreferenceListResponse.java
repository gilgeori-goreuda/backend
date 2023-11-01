package com.pd.gilgeorigoreuda.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberPreferenceListResponse {
    List<MemberPreferenceResponse> preferences;

    private MemberPreferenceListResponse(final List<MemberPreferenceResponse> preferences) {
        this.preferences = preferences;
    }

    public static MemberPreferenceListResponse of(List<MemberPreferenceResponse> preferences) {
        return new MemberPreferenceListResponse(preferences);
    }
}
