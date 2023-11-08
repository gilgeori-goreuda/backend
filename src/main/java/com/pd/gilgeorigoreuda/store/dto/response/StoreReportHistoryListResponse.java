package com.pd.gilgeorigoreuda.store.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StoreReportHistoryListResponse {

    private List<StoreReportHistoryResponse> results;

    private StoreReportHistoryListResponse(final List<StoreReportHistoryResponse> results) {
        this.results = results;
    }

    public static StoreReportHistoryListResponse of(final List<StoreReportHistoryResponse> results) {
        return new StoreReportHistoryListResponse(results);
    }

}
