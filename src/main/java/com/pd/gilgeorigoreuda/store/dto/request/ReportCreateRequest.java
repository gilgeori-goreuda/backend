package com.pd.gilgeorigoreuda.store.dto.request;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;

import java.math.BigDecimal;

public record ReportCreateRequest(
        String content,
        BigDecimal lat,
        BigDecimal lng
) {
    public StoreReportHistory toEntity(Long storeId, Long memberId){
        return StoreReportHistory.builder()
                .member(Member.builder().id(memberId).build())
                .store(Store.builder().id(storeId).build())
                .content(content)
                .build();
    }
}
