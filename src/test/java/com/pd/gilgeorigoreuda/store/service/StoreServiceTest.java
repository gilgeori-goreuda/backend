package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.store.domain.entity.*;
import com.pd.gilgeorigoreuda.store.dto.request.FoodCategoryRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.exception.AlreadyExistInBoundaryException;
import com.pd.gilgeorigoreuda.store.exception.NoSuchStoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class StoreServiceTest extends ServiceTest {

    private static final Integer TEST_BOUNDARY = 10;

    private StoreCreateRequest makeStoreCreateRequest() {
        return new StoreCreateRequest(
                "붕어빵 가게",
                "포장마차",
                LocalTime.of(9, 0),
                LocalTime.of(23, 0),
                "현금",
                "https://image.com",
                "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
                BigDecimal.valueOf(37.123456),
                BigDecimal.valueOf(127.123456),
                "서울특별시 강남구 언주로1",
                new FoodCategoryRequest(List.of("붕어빵", "호떡"))
        );
    }

    private StoreUpdateRequest makeStoreUpdateRequest() {
        return new StoreUpdateRequest(
                "수정된 붕어빵 가게",
                "포장마차",
                LocalTime.of(10, 0),
                LocalTime.of(22, 30),
                "현금",
                "https://newimage.com",
                "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
                BigDecimal.valueOf(37.123456),
                BigDecimal.valueOf(127.123456),
                "서울특별시 강남구 언주로1",
                new FoodCategoryRequest(List.of("붕어빵", "호떡"))
        );
    }

    @Test
    @DisplayName("해당 위경도 기준 반경 10M 범위안에 가게가 없으면 저장한다.")
    void saveWhenStoreIsNotExistInBoundary() {
        // given
        StoreCreateRequest storeCreateRequest = makeStoreCreateRequest();
        StreetAddress streetAddress = StreetAddress.of(storeCreateRequest.getStreetAddress());

        given(storeNativeQueryRepository.isAlreadyExistInBoundary(
                storeCreateRequest.getLat(),
                storeCreateRequest.getLng(),
                streetAddress.getLargeAddress(),
                streetAddress.getMediumAddress(),
                TEST_BOUNDARY)
        ).willReturn(Optional.empty());

        given(storeRepository.save(any(Store.class))).willReturn(ServiceTest.STORE);

        // when
        StoreCreateResponse response = storeService.saveStore(1L, storeCreateRequest);

        // then
        assertNotNull(response);
        assertAll(
                () -> then(storeRepository).should(times(1)).save(any(Store.class)),
                () -> then(storeNativeQueryRepository).should(times(1))
                        .isAlreadyExistInBoundary(
                            storeCreateRequest.getLat(),
                            storeCreateRequest.getLng(),
                            streetAddress.getLargeAddress(),
                            streetAddress.getMediumAddress(),
                            TEST_BOUNDARY
                        )
        );
    }

    @Test
    @DisplayName("해당 위경도 기준 반경 10M 범위안에 이미 가게가 존재하면 예외가 발생한다.")
    void shouldThrowExceptionWhenStoreAlreadyExistInBoundary() {
        // given
        StoreCreateRequest storeCreateRequest = makeStoreCreateRequest();
        StreetAddress streetAddress = StreetAddress.of(storeCreateRequest.getStreetAddress());

        given(storeNativeQueryRepository.isAlreadyExistInBoundary(
                storeCreateRequest.getLat(),
                storeCreateRequest.getLng(),
                streetAddress.getLargeAddress(),
                streetAddress.getMediumAddress(),
                TEST_BOUNDARY)
        ).willReturn(Optional.of(1L));

        // when & then
        assertThatThrownBy(() -> storeService.saveStore(1L, storeCreateRequest))
                .isInstanceOf(AlreadyExistInBoundaryException.class)
                .extracting("errorCode")
                .isEqualTo("S006");

        then(storeRepository).should(never()).save(any(Store.class));
    }

    @Test
    @DisplayName("가게 정보 업데이트 호출 시 id를 검증하고 save 메소드 호출")
    void updateStoreSuccessful() {
        // given
        StoreUpdateRequest storeUpdateRequest = makeStoreUpdateRequest();
        StreetAddress streetAddress = StreetAddress.of(storeUpdateRequest.getStreetAddress());

        given(storeRepository.findStoreWithMemberAndCategories(anyLong()))
                .willReturn(Optional.of(ServiceTest.STORE));

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.of(ServiceTest.MEMBER));

        given(storeNativeQueryRepository.isAlreadyExistInBoundary(
                storeUpdateRequest.getLat(),
                storeUpdateRequest.getLng(),
                streetAddress.getLargeAddress(),
                streetAddress.getMediumAddress(),
                TEST_BOUNDARY)
        ).willReturn(Optional.empty());

        // when
        storeService.updateStore(1L, 1L, storeUpdateRequest);

        // then
        assertAll(
                () -> then(storeRepository).should(times(1)).findStoreWithMemberAndCategories(anyLong()),
                () -> then(memberRepository).should(times(1)).findById(anyLong()),
                () -> then(storeRepository).should(times(1)).save(any(Store.class))
        );
    }

    @Test
    @DisplayName("유효하지 않은 가게 id로 가게 정보 업데이트 호출 시 예외가 발생한다.")
    void shouldThrowExceptionWhenStoreIdIsInvalid() {
        // given
        StoreUpdateRequest storeUpdateRequest = makeStoreUpdateRequest();

        given(storeRepository.findStoreWithMemberAndCategories(1L))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storeService.updateStore(1L, 1L, storeUpdateRequest))
                .isInstanceOf(NoSuchStoreException.class)
                .extracting("errorCode")
                .isEqualTo("S001");

        then(storeRepository).should(never()).save(any(Store.class));
    }

}