package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.common.util.DistanceCalculator;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import com.pd.gilgeorigoreuda.store.dto.request.ReportCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryListResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreReportHistoryResponse;
import com.pd.gilgeorigoreuda.store.exception.*;
import com.pd.gilgeorigoreuda.store.repository.StoreReportHistoryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreReportService {

    private final StoreReportHistoryRepository storeReportRepository;
    private final StoreRepository storeRepository;
    private final DistanceCalculator distanceCalculator;

    private static final Integer BOUNDARY_100M = 100;

    @Transactional
    public void createStoreReport(final ReportCreateRequest reportCreateRequest, final Long storeId, final Long memberId) {
        checkIsExistStoreReportHistory(storeId, memberId);

        Store targetStore = findTargetStore(storeId);

        checkIsValidDistanceForReport(reportCreateRequest, targetStore);

        storeReportRepository.save(reportCreateRequest.toEntity(storeId, memberId));
    }

    @Transactional
    public void deleteReport(final Long reportId, final Long memberId) {
        StoreReportHistory reportForDelete = findStoreReportHistory(reportId);

        checkIsReporter(memberId, reportForDelete);

        storeReportRepository.deleteById(reportForDelete.getId());
    }

    private static void checkIsReporter(Long memberId, StoreReportHistory reportForDelete) {
        if (!reportForDelete.isReporter(memberId)) {
            throw new NoRepoterMemberException();
        }
    }

    private void checkIsValidDistanceForReport(final ReportCreateRequest reportCreateRequest, final Store targetStore) {
        int betweenDistance = distanceCalculator.getDistance(
                reportCreateRequest.getMemberLat(),
                reportCreateRequest.getMemberLng(),
                targetStore.getLat(),
                targetStore.getLng()
        );

        if(betweenDistance > BOUNDARY_100M) {
            throw new TooLongDistanceToReportException();
        }
    }

    private Store findTargetStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(NoSuchStoreException::new);
    }

    private void checkIsExistStoreReportHistory(final Long storeId, final Long memberId) {
        storeReportRepository.findStoreReportHistoryByStoreIdAndMemberId(storeId, memberId)
                .ifPresent(report -> {
                    throw new AlreadyReportedException();
                });
    }

    private StoreReportHistory findStoreReportHistory(final Long reportId) {
        return storeReportRepository.findStoreReportHistoryByReportId(reportId)
                .orElseThrow(NoSuchReportException::new);
    }

    public StoreReportHistoryListResponse checkMemberReportList(final Long memberId){
        List<StoreReportHistoryResponse> results = storeReportRepository.findStoreReportHistoriesByMemberId(memberId)
                .stream()
                .map(StoreReportHistoryResponse::of)
                .toList();

        return StoreReportHistoryListResponse.of(results);
    }
    public StoreReportHistoryListResponse checkStoreReportList(final Long storeId){
        List<StoreReportHistoryResponse> results = storeReportRepository.findStoreReportHistoriesByStoreId(storeId)
                .stream()
                .map(StoreReportHistoryResponse::of)
                .toList();

        return StoreReportHistoryListResponse.of(results);
    }


}