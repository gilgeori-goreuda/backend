package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.exception.NoSuchMemberException;
import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.store.domain.entity.*;
import com.pd.gilgeorigoreuda.store.dto.request.FoodCategoryRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
import com.pd.gilgeorigoreuda.store.exception.AlreadyExistInBoundaryException;
import com.pd.gilgeorigoreuda.store.exception.NoOwnerMemberException;
import com.pd.gilgeorigoreuda.store.exception.NoSuchStoreException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

class StoreServiceTest extends ServiceTest {

    private static final Integer TEST_BOUNDARY = 10;

    private static final BigDecimal memberLat = new BigDecimal("37.1234");
    private static final BigDecimal memberLng = new BigDecimal("127.5678");

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
    void updateStoreSuccess() {
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
    @DisplayName("유효하지 않은 가게 id로 가게 정보 업데이트 호출 시 예외가 발생")
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

    @Test
    @DisplayName("유효하지 않은 회원 id로 가게 정보 업데이트 호출 시 예외가 발생")
    void shouldThrowExceptionWhenMemberIdIsInvalid() {
        // given
        StoreUpdateRequest storeUpdateRequest = makeStoreUpdateRequest();

        given(storeRepository.findStoreWithMemberAndCategories(any()))
                .willReturn(Optional.of(StoreServiceTest.STORE));

        given(memberRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storeService.updateStore(1L, 1L, storeUpdateRequest))
                .isInstanceOf(NoSuchMemberException.class)
                .extracting("errorCode")
                .isEqualTo("M001");

        then(storeRepository).should(never()).save(any(Store.class));
    }

    @Nested
    @DisplayName("가게 정보 변경 시 카테고리, 이미지 업데이트 테스트")
    class UpdateStoreInfo {

        private final Member member = ServiceTest.MEMBER;
        private final Store store = ServiceTest.STORE;

        // Store 객체의 메소드 isSameImage를 Mocking 하기 위한 Store Mock 객체
        private final Store mockStore = mock(Store.class);

        private StoreUpdateRequest newImageRequest;
        private StoreUpdateRequest sameImageRequest;
        private StoreUpdateRequest newCategoryRequest;

        private StoreUpdateRequest makeNewImageStoreUpdateRequest() {
            return new StoreUpdateRequest(
                    "수정된 붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 30),
                    "현금",
                    "https://newImage.com",
                    "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
                    BigDecimal.valueOf(37.123456),
                    BigDecimal.valueOf(127.123456),
                    "서울특별시 강남구 언주로1",
                    new FoodCategoryRequest(List.of("붕어빵", "호떡"))
            );
        }

        private StoreUpdateRequest makeSameImageStoreUpdateRequest() {
            return new StoreUpdateRequest(
                    "수정된 붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 30),
                    "현금",
                    "https://image.com",
                    "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
                    BigDecimal.valueOf(37.123456),
                    BigDecimal.valueOf(127.123456),
                    "서울특별시 강남구 언주로1",
                    new FoodCategoryRequest(List.of("붕어빵", "호떡"))
            );
        }

        private StoreUpdateRequest makeNewCategoryStoreUpdateRequest() {
            return new StoreUpdateRequest(
                    "수정된 붕어빵 가게",
                    "포장마차",
                    LocalTime.of(10, 0),
                    LocalTime.of(22, 30),
                    "현금",
                    "https://image.com",
                    "monday,tuesday,wednesday,thursday,friday,saturday,sunday",
                    BigDecimal.valueOf(37.123456),
                    BigDecimal.valueOf(127.123456),
                    "서울특별시 강남구 언주로1",
                    new FoodCategoryRequest(List.of("오뎅", "타코야끼"))
            );
        }

        @BeforeEach
        void setUp() {
            newImageRequest = makeNewImageStoreUpdateRequest();
            sameImageRequest = makeSameImageStoreUpdateRequest();
            newCategoryRequest = makeNewCategoryStoreUpdateRequest();
        }

        @Test
        @DisplayName("이미지가 변경되었을 경우 기존 이미지 삭제")
        void deleteOriginalImageWhenImageIsChanged() {
            // given
            given(storeRepository.findStoreWithMemberAndCategories(anyLong()))
                    .willReturn(Optional.of(mockStore));

            given(memberRepository.findById(anyLong()))
                    .willReturn(Optional.of(member));

            given(storeNativeQueryRepository.isAlreadyExistInBoundary(
                    newImageRequest.getLat(),
                    newImageRequest.getLng(),
                    StreetAddress.of(newImageRequest.getStreetAddress()).getLargeAddress(),
                    StreetAddress.of(newImageRequest.getStreetAddress()).getMediumAddress(),
                    TEST_BOUNDARY)
            ).willReturn(Optional.empty());

            given(mockStore.isSameImage(mockStore.getImageUrl())).willReturn(false);

            // when
            storeService.updateStore(member.getId(), mockStore.getId(), newImageRequest);

            // then
            then(imageService).should(times(1)).deleteSingleImage(mockStore.getImageUrl());
        }

        @Test
        @DisplayName("이미지가 변경되지 않았을 경우 기존 이미지 삭제하지 않음")
        void notDeleteOriginalImageWhenImageIsNotChanged() {
            // given
            given(storeRepository.findStoreWithMemberAndCategories(anyLong()))
                    .willReturn(Optional.of(mockStore));

            given(memberRepository.findById(anyLong()))
                    .willReturn(Optional.of(member));

            given(storeNativeQueryRepository.isAlreadyExistInBoundary(
                    sameImageRequest.getLat(),
                    sameImageRequest.getLng(),
                    StreetAddress.of(sameImageRequest.getStreetAddress()).getLargeAddress(),
                    StreetAddress.of(sameImageRequest.getStreetAddress()).getMediumAddress(),
                    TEST_BOUNDARY)
            ).willReturn(Optional.empty());

            given(mockStore.isSameImage(mockStore.getImageUrl())).willReturn(true);

            // when
            storeService.updateStore(member.getId(), mockStore.getId(), sameImageRequest);

            // then
            then(imageService).should(never()).deleteSingleImage(anyString());
        }

        @Test
        @DisplayName("카테고리가 변경되었을 경우")
        void deleteOriginalCategoriesWhenCategoriesIsChanged() {
            // given
            given(storeRepository.findStoreWithMemberAndCategories(anyLong()))
                    .willReturn(Optional.of(store));

            given(memberRepository.findById(anyLong()))
                    .willReturn(Optional.of(member));

            given(storeNativeQueryRepository.isAlreadyExistInBoundary(
                    newCategoryRequest.getLat(),
                    newCategoryRequest.getLng(),
                    StreetAddress.of(newCategoryRequest.getStreetAddress()).getLargeAddress(),
                    StreetAddress.of(newCategoryRequest.getStreetAddress()).getMediumAddress(),
                    TEST_BOUNDARY)
            ).willReturn(Optional.empty());

            // when
            storeService.updateStore(store.getId(), member.getId(), newCategoryRequest);

            // then
            assertSoftly(
                    softly -> {
                        softly.assertThat(store.getFoodCategories()).hasSize(2);
                        softly.assertThat(store.getFoodCategories())
                                .extracting("foodType")
                                .usingRecursiveComparison()
                                .ignoringCollectionOrder()
                                .isEqualTo(List.of("오뎅", "타코야끼"));
                    }
            );
        }
    }

    @Nested
    @DisplayName("가게 삭제 테스트")
    class DeleteStore {

        private final Member member = ServiceTest.MEMBER;
        private final Store store = ServiceTest.STORE;

        // Store 객체의 메소드를 Mocking 하기 위한 Store Mock 객체
        private final Store storeMock = mock(Store.class);


        @Test
        @DisplayName("가게 제보자인 경우만 삭제 성공")
        void deleteStoreSuccessWhenMemberIsReporter() {
            // given
            given(storeRepository.findStoreWithMember(anyLong()))
                    .willReturn(Optional.of(storeMock));

            given(storeMock.isOwner(member.getId()))
                    .willReturn(true);

            // when
            storeService.deleteStore(member.getId(), storeMock.getId());

            // then
            then(storeRepository).should(times(1)).deleteById(anyLong());
        }

        @Test
        @DisplayName("가게 제보자가 아닌 경우 삭제 실패")
        void deleteStoreFailWhenMemberIsNotReporter() {
            // given
            given(storeRepository.findStoreWithMember(anyLong()))
                    .willReturn(Optional.of(storeMock));

            given(storeMock.isOwner(member.getId()))
                    .willReturn(false);

            // when & then
            assertThatThrownBy(() -> storeService.deleteStore(member.getId(), storeMock.getId()))
                    .isInstanceOf(NoOwnerMemberException.class)
                    .extracting("errorCode")
                    .isEqualTo("S005");

            then(storeRepository).should(never()).deleteById(anyLong());
        }

        @Test
        @DisplayName("가게 삭제 시 기존 이미지 삭제")
        void deleteOriginalImageWhenStoreIsDeleted() {
            // given
            given(storeRepository.findStoreWithMember(anyLong()))
                    .willReturn(Optional.of(store));

            // when
            storeService.deleteStore(member.getId(), store.getId());

            // then
            then(imageService).should(times(1)).deleteSingleImage(anyString());
        }
    }

    @Test
    @DisplayName("storeId에 해당하는 Store 정보 반환")
    void getStoreSuccess() {
        // given
        given(storeRepository.findStoreWithMemberAndCategories(ServiceTest.STORE.getId()))
                .willReturn(Optional.of(ServiceTest.STORE));

        // when
        StoreResponse storeResponse = storeService.getStore(1L, memberLat, memberLng);

        // then
        assertThat(storeResponse).isNotNull();
        assertThat(storeResponse.getId()).isEqualTo(ServiceTest.STORE.getId());
    }

    @Test
    @DisplayName("유효하지 않는 id로 가게 조회시 예외가 발생")
    void shouldThrowExceptionWhenStoreIdInvalid() {
        // given
        given(storeRepository.findStoreWithMemberAndCategories(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> storeService.getStore(1L, memberLat, memberLng))
                .isInstanceOf(NoSuchStoreException.class)
                .extracting("errorCode")
                .isEqualTo("S001");
    }

    @Test
    @DisplayName("가게 정보 반환 시 가게와 사용자의 거리 반환")
    void getStoreWithDistance() {
        // given
        given(storeRepository.findStoreWithMemberAndCategories(ServiceTest.STORE.getId()))
                .willReturn(Optional.of(ServiceTest.STORE));

        // when
        StoreResponse storeResponse = storeService.getStore(1L, memberLat, memberLng);

        // then
        assertThat(storeResponse).isNotNull();
        assertThat(storeResponse.getDistanceFromMember()).isNotNull();
    }

}