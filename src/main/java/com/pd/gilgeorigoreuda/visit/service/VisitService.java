package com.pd.gilgeorigoreuda.visit.service;

import com.pd.gilgeorigoreuda.common.util.DistanceCalculator;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import com.pd.gilgeorigoreuda.store.exception.NoSuchStoreException;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitRequest;
import com.pd.gilgeorigoreuda.visit.exception.OutOfBoundaryException;
import com.pd.gilgeorigoreuda.visit.repository.StoreVisitRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class VisitService {

    private final StoreVisitRecordRepository storeVisitRecordRepository;
    private final StoreRepository storeRepository;

    private static final int BOUNDARY = 10;

    @Transactional
    public void visit(final Long memberId, final Long storeId, final VisitRequest visitRequest) {
        Store targetStore = getTargetStore(storeId);

        BigDecimal memberLat = visitRequest.getLat();
        BigDecimal memberLng = visitRequest.getLng();

        BigDecimal targetStoreLat = targetStore.getLat();
        BigDecimal targetStoreLng = targetStore.getLng();

        int walkingDistance = getDistanceBetweenStoreAndMember(memberLat, memberLng, targetStoreLat, targetStoreLng);

        StoreVisitRecord storeVisitRecord = StoreVisitRecord.from(memberId, storeId, walkingDistance);

        storeVisitRecordRepository.save(storeVisitRecord);
    }

    private static int getDistanceBetweenStoreAndMember(final BigDecimal memberLat, final BigDecimal memberLng,
                                           final BigDecimal targetStoreLat, final BigDecimal targetStoreLng) {
        return DistanceCalculator.calculateDistance(memberLat, memberLng, targetStoreLat, targetStoreLng);
    }

    private Store getTargetStore(Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(NoSuchStoreException::new);
    }

}
