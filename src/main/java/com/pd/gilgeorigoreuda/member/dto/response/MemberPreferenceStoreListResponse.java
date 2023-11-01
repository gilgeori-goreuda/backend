package com.pd.gilgeorigoreuda.member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberPreferenceStoreListResponse {

    private List<MemberPreferenceStoreResponse> preferences;

    private MemberPreferenceStoreListResponse(final List<MemberPreferenceStoreResponse> preferences) {
        this.preferences = preferences;
    }

    public static MemberPreferenceStoreListResponse of(List<MemberPreferenceStoreResponse> preferences) {
        return new MemberPreferenceStoreListResponse(preferences);
    }

}
