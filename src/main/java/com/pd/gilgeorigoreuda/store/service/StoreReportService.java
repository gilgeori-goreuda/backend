package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.repository.StoreReportHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreReportService {

    private final StoreReportHistoryRepository storeReportRepository;

    public void addStoreReport(ReportCreateRequest reportCreateRequest, Long storeId,
                                Long memberId) {
        storeReportRepository.save(reportCreateRequest.toEntity(storeId, memberId));
    }
}