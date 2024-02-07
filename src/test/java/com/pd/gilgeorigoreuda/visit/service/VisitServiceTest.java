package com.pd.gilgeorigoreuda.visit.service;

import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitRequest;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitVerifyRequest;
import com.pd.gilgeorigoreuda.visit.dto.response.VisitResponse;
import com.pd.gilgeorigoreuda.visit.exception.DuplicatedVisitRecordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreVisitRecordFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class VisitServiceTest extends ServiceTest {

    private static final int VALID_BOUNDARY_METER = 300;
    private static final int VALID_TIME_HOUR = 2;
    public static final int MAX_APPROXIMATE_WALKING_TIME_HOUR = 4;
    public static final int EXCEED_WALKING_TIME_HOUR = 6;


    private VisitRequest makeVisitRequest() {
        return new VisitRequest(BigDecimal.valueOf(37.49732853932101), BigDecimal.valueOf(127.02821485508554));
    }

    private VisitVerifyRequest makeVisitVerifyRequest() {
        return new VisitVerifyRequest(BigDecimal.valueOf(37.49728798321963), BigDecimal.valueOf(127.02826007403824));
    }

    @Test
    @DisplayName("중복된 방문 기록이 없다면 생성 성공")
    void createVisitRecordSuccessWhenRecordIsNotDuplicated() {
        // given
        VisitRequest visitRequest = makeVisitRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(storeVisitRecordRepository.findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()))
                .willReturn(Optional.empty());

        given(storeVisitRecordRepository.save(any(StoreVisitRecord.class)))
                .willReturn(KIMS_BUNGEOPPANG_VISIT_RECORD());

        // when
        VisitResponse visitResponse = visitService.visit(KIM().getId(), BUNGEOPPANG().getId(), visitRequest);

        // then
        assertAll(
                () -> assertNotNull(visitResponse),
                () -> then(storeRepository).should().findById(BUNGEOPPANG().getId()),
                () -> then(storeVisitRecordRepository).should().findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()),
                () -> then(storeVisitRecordRepository).should().save(any(StoreVisitRecord.class))
        );

    }

    @Test
    @DisplayName("중복된 방문 기록이 있다면 생성 실패")
    void createVisitRecordFailWhenRecordIsDuplicated() {
        // given
        VisitRequest visitRequest = makeVisitRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(storeVisitRecordRepository.findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()))
                .willReturn(Optional.of(KIMS_BUNGEOPPANG_VISIT_RECORD()));

        // when & then
        assertThatThrownBy(() -> visitService.visit(KIM().getId(), BUNGEOPPANG().getId(), visitRequest))
                .isInstanceOf(DuplicatedVisitRecordException.class)
                .extracting("errorCode")
                .isEqualTo("V006");

        then(storeVisitRecordRepository).should(never()).save(any(StoreVisitRecord.class));
    }

}