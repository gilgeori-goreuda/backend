package com.pd.gilgeorigoreuda.store.dto.request;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;

public record ReportCreateRequest(
        String content
) {
    public StoreReportHistory toEntity(Long storeId, Long memberId){
        return StoreReportHistory.builder()
                .member(Member.builder().id(memberId).build())
                .store(Store.builder().id(storeId).build())
                .content(content)
                .build();
    }
}
