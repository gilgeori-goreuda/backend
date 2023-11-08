package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchListResponse;
import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchResponse;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryListResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryResponse;
import com.pd.gilgeorigoreuda.store.exception.NoRepoterMemberException;
import com.pd.gilgeorigoreuda.store.exception.NoSuchReportException;
import com.pd.gilgeorigoreuda.store.repository.StoreReportHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        StoreReportHistory reportForDelete = findReportWithMemberConditionReportId(reportId);

        if (!reportForDelete.isRepoter(memberId)) {
            throw new NoRepoterMemberException();
        }
        storeReportRepository.deleteById(reportForDelete.getId());
    }

    private StoreReportHistory findReportWithMemberConditionReportId(Long reportId) {
        return storeReportRepository.findReportWithMemberConditionReportId(reportId)
                .orElseThrow(NoSuchReportException::new);
    }

    public StoreReportHistoryListResponse checkMemberReportList(final Long memberId){
        List<StoreReportHistoryResponse> results = storeReportRepository.findReportWithMemberConditionMemberId(memberId)
                .stream()
                .map(StoreReportHistoryResponse::of)
                .toList();

        return StoreReportHistoryListResponse.of(results);
    }


}