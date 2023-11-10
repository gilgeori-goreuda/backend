package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.common.util.DistanceCalculator;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryListResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryResponse;
import com.pd.gilgeorigoreuda.store.exception.*;
import com.pd.gilgeorigoreuda.store.repository.StoreReportHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreReportService {

    private final StoreReportHistoryRepository storeReportRepository;

    @Transactional
    public void addStoreReport(ReportCreateRequest reportCreateRequest, Long storeId,
                                Long memberId) {
        Optional<StoreReportHistory> reportAlreadyReported =
                storeReportRepository.findReportAlreadyReported(storeId, memberId);

        if (reportAlreadyReported.isPresent()) {
            throw new AlreadyReporterMemberException();
        }

        BigDecimal memberLat = reportCreateRequest.lat();
        BigDecimal memberLng = reportCreateRequest.lng();
        
        Optional<Store> reportLimitDistance = storeReportRepository.findReportLimitDistance(storeId);
        if (!reportLimitDistance.isPresent()) {
            throw new NoSuchStoreException();
        }
        BigDecimal storeLat = reportLimitDistance.get().getLat();
        BigDecimal storeLng = reportLimitDistance.get().getLng();

        int betweenDistance = DistanceCalculator.calculateDistance(memberLat, memberLng, storeLat, storeLng);
        if(betweenDistance > 100) {
            throw new LimitDistanceReportException();
        }

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
    public StoreReportHistoryListResponse checkStoreReportList(final Long storeId){
        List<StoreReportHistoryResponse> results = storeReportRepository.findReportWithMemberConditionStoreId(storeId)
                .stream()
                .map(StoreReportHistoryResponse::of)
                .toList();

        return StoreReportHistoryListResponse.of(results);
    }


}