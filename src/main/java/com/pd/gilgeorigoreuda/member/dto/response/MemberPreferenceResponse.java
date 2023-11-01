
package com.pd.gilgeorigoreuda.member.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberPreferenceResponse {
    private StorePreferenceType storePreferenceType; // 좋아요만

    private List<Store> stores;

    public static MemberPreferenceResponse of(List<Store> stores) {
        return new MemberPreferenceResponse(StorePreferenceType.PREFERRED, stores);
    }
}