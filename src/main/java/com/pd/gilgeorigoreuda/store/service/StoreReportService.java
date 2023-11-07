package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.exception.NoOwnerMemberException;
import com.pd.gilgeorigoreuda.store.exception.NoRepoterMemberException;
import com.pd.gilgeorigoreuda.store.exception.NoSuchStoreException;
import com.pd.gilgeorigoreuda.store.repository.StoreReportHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreReportService {

    private final StoreReportHistoryRepository storeReportRepository;

    @Transactional
    public void addStoreReport(ReportCreateRequest reportCreateRequest, Long storeId,
                                Long memberId) {
        storeReportRepository.save(reportCreateRequest.toEntity(storeId, memberId));
    }

    @Transactional
    public void deleteReport(final Long reportId, final Long memberId) {
        StoreReportHistory reportForDelete = findReportWithMember(reportId);

        if (!reportForDelete.isRepoter(memberId)) {
            throw new NoRepoterMemberException();
        }
        storeReportRepository.deleteById(reportForDelete.getId());
    }

    private StoreReportHistory findReportWithMember(Long reportId) {
        return storeReportRepository.findReportWithMember(reportId)
                .orElseThrow(NoSuchStoreException::new);
    }

}