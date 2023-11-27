
package com.pd.gilgeorigoreuda.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberPreferenceStoreResponse {

    private Long preferenceId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    private PreferredStoreInfoResponse preferredStoreInfo;

    public MemberPreferenceStoreResponse(
            final Long preferenceId,
            final LocalDateTime createdAt,
            final PreferredStoreInfoResponse preferredStoreInfo) {
        this.preferenceId = preferenceId;
        this.createdAt = createdAt;
        this.preferredStoreInfo = preferredStoreInfo;
    }

    public static MemberPreferenceStoreResponse of(final StorePreference storePreference) {
        return new MemberPreferenceStoreResponse(
                storePreference.getId(),
                storePreference.getCreatedAt(),
                PreferredStoreInfoResponse.of(storePreference.getStore())
        );
    }

}