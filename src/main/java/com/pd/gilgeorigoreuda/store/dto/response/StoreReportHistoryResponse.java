package com.pd.gilgeorigoreuda.store.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StoreReportHistoryResponse {

    private Long id;
    private String content;
    private StoreReportMemberResponse member;
    private StoreReportStoreResponse store;

    public StoreReportHistoryResponse(
            final Long id,
            final String content,
            final StoreReportMemberResponse member,
            final StoreReportStoreResponse store) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.store = store;
    }

    public static StoreReportHistoryResponse of(final StoreReportHistory storeReportHistory) {
        return new StoreReportHistoryResponse(
                storeReportHistory.getId(),
                storeReportHistory.getContent(),
                StoreReportMemberResponse.of(storeReportHistory.getMember()),
                StoreReportStoreResponse.of(storeReportHistory.getStore())
        );
    }

}
