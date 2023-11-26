package com.pd.gilgeorigoreuda.store.dto.response;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class StoreReportHistoryResponse {

    private Long id;
    private String content;
    private StoreReportMemberResponse member;
    private StoreReportStoreResponse store;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public StoreReportHistoryResponse(final Long id,
                                      final String content,
                                      final StoreReportMemberResponse member,
                                      final StoreReportStoreResponse store,
                                      final LocalDateTime createdAt,
                                      final LocalDateTime modifiedAt) {
        this.id = id;
        this.content = content;
        this.member = member;
        this.store = store;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static StoreReportHistoryResponse of(final StoreReportHistory storeReportHistory) {
        return new StoreReportHistoryResponse(
                storeReportHistory.getId(),
                storeReportHistory.getContent(),
                StoreReportMemberResponse.of(storeReportHistory.getMember()),
                StoreReportStoreResponse.of(storeReportHistory.getStore()),
                storeReportHistory.getCreatedAt(),
                storeReportHistory.getModifiedAt()
        );
    }

}
