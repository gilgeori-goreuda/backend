package com.pd.gilgeorigoreuda.visit.service;

import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitRequest;
import com.pd.gilgeorigoreuda.visit.dto.request.VisitVerifyRequest;
import com.pd.gilgeorigoreuda.visit.dto.response.VisitResponse;
import com.pd.gilgeorigoreuda.visit.exception.DuplicatedVisitRecordException;
import com.pd.gilgeorigoreuda.visit.exception.OutOfBoundaryException;
import com.pd.gilgeorigoreuda.visit.exception.TimeOutException;
import com.pd.gilgeorigoreuda.visit.exception.TooLongDistanceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreVisitRecordFixtures.KIMS_BUNGEOPPANG_VISIT_RECORD;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class VisitServiceTest extends ServiceTest {

    private static final int VALID_BOUNDARY_METER = 20;
    private static final int INVALID_BOUNDARY_METER = 100;
    private static final int VALID_TIME_HOUR = 2;
    private static final int VALID_WALKING_TIME_HOUR = 4;
    private static final int INVALID_WALKING_TIME_HOUR = 6;
    private static final int VALID_WALKING_DISTANCE = 100;
    private static final int INVALID_WALKING_DISTANCE = 999999999;

    private StoreVisitRecord mockStoreVisitRecord = mock(StoreVisitRecord.class);

    // StoreVisitRecord 클래스의 createdAt 필드에 접근하기 위한 메서드
    private Field getSuperClassCreatedAtField() throws NoSuchFieldException {
        Field createdAt = StoreVisitRecord.class.getSuperclass().getDeclaredField("createdAt");
        createdAt.setAccessible(true);

        return createdAt;
    }

    private StoreVisitRecord getValidTimeStoreVisitRecord() throws NoSuchFieldException, IllegalAccessException {
        Field createdAt = getSuperClassCreatedAtField();
        StoreVisitRecord storeVisitRecord = KIMS_BUNGEOPPANG_VISIT_RECORD();

        createdAt.set(storeVisitRecord, LocalDateTime.now().minusMinutes(30));

        return storeVisitRecord;
    }

    private StoreVisitRecord getInValidTimeStoreVisitRecord() throws NoSuchFieldException, IllegalAccessException {
        Field createdAt = getSuperClassCreatedAtField();
        StoreVisitRecord storeVisitRecord = KIMS_BUNGEOPPANG_VISIT_RECORD();

        createdAt.set(storeVisitRecord, LocalDateTime.now().minusHours(4));

        return storeVisitRecord;
    }

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

        given(distanceCalculator.getDistance(any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(VALID_WALKING_DISTANCE);

        given(distanceCalculator.getApproximateWalkingTime(VALID_WALKING_DISTANCE))
                .willReturn(VALID_WALKING_TIME_HOUR);

        given(storeVisitRecordRepository.save(any(StoreVisitRecord.class)))
                .willReturn(KIMS_BUNGEOPPANG_VISIT_RECORD());

        // when
        VisitResponse visitResponse = visitService.visit(KIM().getId(), BUNGEOPPANG().getId(), visitRequest);

        // then
        assertAll(
                () -> assertNotNull(visitResponse),
                () -> then(storeRepository).should().findById(BUNGEOPPANG().getId()),
                () -> then(storeVisitRecordRepository).should().findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()),
                () -> then(distanceCalculator).should().getDistance(visitRequest.getLat(), visitRequest.getLng(), BUNGEOPPANG().getLat(), BUNGEOPPANG().getLng()),
                () -> then(distanceCalculator).should().getApproximateWalkingTime(VALID_WALKING_DISTANCE),
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

    @Test
    @DisplayName("도보 시간이 4시간 초과라면 방문 기록 생성 성공")
    void createVisitRecordSuccessWhenWalkingTimeIsOver4Hours() {
        // given
        VisitRequest visitRequest = makeVisitRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(storeVisitRecordRepository.findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()))
                .willReturn(Optional.empty());

        given(distanceCalculator.getDistance(any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(INVALID_WALKING_DISTANCE);

        given(distanceCalculator.getApproximateWalkingTime(INVALID_WALKING_DISTANCE))
                .willReturn(INVALID_WALKING_TIME_HOUR);

        // when & then
        assertThatThrownBy(() -> visitService.visit(KIM().getId(), BUNGEOPPANG().getId(), visitRequest))
                .isInstanceOf(TooLongDistanceException.class)
                .extracting("errorCode")
                .isEqualTo("V004");

        then(storeVisitRecordRepository).should(never()).save(any(StoreVisitRecord.class));
    }

    @Test
    @DisplayName("사용자 위치가 가게 반경 20m 이내면 가게 방문 인증 성공")
    void verifyVisitSuccessWhenMemberIsInBoundaryAnd() throws NoSuchFieldException, IllegalAccessException {
        // given
        StoreVisitRecord storeVisitRecord = getValidTimeStoreVisitRecord();

        VisitVerifyRequest visitVerifyRequest = makeVisitVerifyRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(distanceCalculator.getDistance(any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(VALID_BOUNDARY_METER);

        given(storeVisitRecordRepository.findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()))
                .willReturn(Optional.of(storeVisitRecord));

        // when
        visitService.verifyVisit(KIM().getId(), BUNGEOPPANG().getId(), visitVerifyRequest);

        // then
        assertAll(
                () -> then(storeRepository).should().findById(BUNGEOPPANG().getId()),
                () -> then(distanceCalculator).should().getDistance(visitVerifyRequest.getLat(), visitVerifyRequest.getLng(), BUNGEOPPANG().getLat(), BUNGEOPPANG().getLng()),
                () -> then(storeVisitRecordRepository).should().findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId())
        );
    }

    @Test
    @DisplayName("사용자 위치가 가게 반경 20m 이내가 아니라면 가게 방문 인증 실패")
    void verifyVisitFailWhenMemberIsOutOfBoundary() throws NoSuchFieldException, IllegalAccessException {
        // given
        StoreVisitRecord storeVisitRecord = getValidTimeStoreVisitRecord();

        VisitVerifyRequest visitVerifyRequest = makeVisitVerifyRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(distanceCalculator.getDistance(any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(INVALID_BOUNDARY_METER);

        // when & then
        assertThatThrownBy(() -> visitService.verifyVisit(KIM().getId(), BUNGEOPPANG().getId(), visitVerifyRequest))
                .isInstanceOf(OutOfBoundaryException.class)
                .extracting("errorCode")
                .isEqualTo("V001");

        assertAll(
                () -> then(storeVisitRecordRepository).should(never()).findByMemberIdAndStoreId(anyLong(), anyLong()),
                () -> then(mockStoreVisitRecord).should(never()).verifyVisit()
        );
    }

    @Test
    @DisplayName("방문 기록 생성된지 2시간 이내라면 가게 방문 인증 성공")
    void verifyVisitSuccessWhenVisitRecordIsCreatedWithin2Hours() throws NoSuchFieldException, IllegalAccessException {
        // given
        StoreVisitRecord storeVisitRecord = getValidTimeStoreVisitRecord();

        VisitVerifyRequest visitVerifyRequest = makeVisitVerifyRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(distanceCalculator.getDistance(any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(VALID_BOUNDARY_METER);

        given(storeVisitRecordRepository.findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()))
                .willReturn(Optional.of(storeVisitRecord));

        // when
        visitService.verifyVisit(KIM().getId(), BUNGEOPPANG().getId(), visitVerifyRequest);

        // then
        assertAll(
                () -> then(storeRepository).should().findById(BUNGEOPPANG().getId()),
                () -> then(distanceCalculator).should().getDistance(visitVerifyRequest.getLat(), visitVerifyRequest.getLng(), BUNGEOPPANG().getLat(), BUNGEOPPANG().getLng()),
                () -> then(storeVisitRecordRepository).should().findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId())
        );
    }

    @Test
    @DisplayName("방문 기록 생성된지 2시간 이상이라면 가게 방문 인증 실패")
    void verifyVisitFailWhenVisitRecordIsCreatedOver2Hours() throws NoSuchFieldException, IllegalAccessException {
        // given
        StoreVisitRecord storeVisitRecord = getInValidTimeStoreVisitRecord();

        VisitVerifyRequest visitVerifyRequest = makeVisitVerifyRequest();

        given(storeRepository.findById(BUNGEOPPANG().getId()))
                .willReturn(Optional.of(BUNGEOPPANG()));

        given(distanceCalculator.getDistance(any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class), any(BigDecimal.class)))
                .willReturn(VALID_BOUNDARY_METER);

        given(storeVisitRecordRepository.findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()))
                .willReturn(Optional.of(storeVisitRecord));

        // when & then
        assertThatThrownBy(() -> visitService.verifyVisit(KIM().getId(), BUNGEOPPANG().getId(), visitVerifyRequest))
                .isInstanceOf(TimeOutException.class)
                .extracting("errorCode")
                .isEqualTo("V003");

        assertAll(
                () -> then(storeVisitRecordRepository).should().findByMemberIdAndStoreId(KIM().getId(), BUNGEOPPANG().getId()),
                () -> then(mockStoreVisitRecord).should(never()).verifyVisit()
        );
    }

}