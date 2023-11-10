package com.pd.gilgeorigoreuda.member.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberReviewStoreResponse {

    private Long storeId;
    private String storeName;

    private MemberReviewStoreResponse(final Long storeId, final String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public static MemberReviewStoreResponse of(final Store store) {
        return new MemberReviewStoreResponse(store.getId(), store.getName());
    }

}
