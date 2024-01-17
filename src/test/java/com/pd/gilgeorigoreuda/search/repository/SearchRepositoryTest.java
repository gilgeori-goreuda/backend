package com.pd.gilgeorigoreuda.search.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.settings.RepositoryTest;
import com.pd.gilgeorigoreuda.store.domain.entity.*;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SearchRepositoryTest extends RepositoryTest {

    private static final BigDecimal REFERENCE_LAT = new BigDecimal("37.565366567584924");
    private static final BigDecimal REFERENCE_LNG = new BigDecimal("126.97718688550951");
    private static final Double DISTANCE_1KM = 0.00012754530697130809;
    private static final Integer BOUNDARY_1KM = 1000;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .id(1L)
                .nickname("nickname")
                .socialId("socialId")
                .build();

        Store storeInBoundary1 = Store.builder()
                .id(1L)
                .name("시청역 2번 출구 오뎅 가게")
                .storeType(StoreType.FOOD_STALL)
                .detailLocation("testDetailLocation1")
                .averageRating(4.5)
                .businessDate("monday,friday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image.com")
                .lat(new BigDecimal("37.565762957455874"))
                .lng(new BigDecimal("126.97693774891424"))
                .streetAddress(StreetAddress.of("서울특별시 중구 세종대로22"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.ODENG.getFoodName()).build(),
                                FoodCategory.builder().foodType(FoodType.BUNGEOPPANG.getFoodName()).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        Store storeInBoundary2 = Store.builder()
                .id(2L)
                .name("시청역 3번 출구 오뎅 가게")
                .storeType(StoreType.FOOD_TRUCK)
                .detailLocation("testDetailLocation2")
                .averageRating(4.0)
                .businessDate("monday,sunday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image2.com")
                .lat(new BigDecimal("37.56611434367142"))
                .lng(new BigDecimal("126.97692632163597"))
                .streetAddress(StreetAddress.of("서울특별시 중구 세종대로123"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.ODENG.getFoodName()).build(),
                                FoodCategory.builder().foodType(FoodType.EGGBREAD.getFoodName()).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        Store storeInBoundary3 = Store.builder()
                .id(3L)
                .name("시청역 6번 출구 탕후루 가게")
                .storeType(StoreType.FOOD_TRUCK)
                .detailLocation("testDetailLocation2")
                .averageRating(4.0)
                .businessDate("monday,tuesday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image2.com")
                .lat(new BigDecimal("37.56479901162554"))
                .lng(new BigDecimal("126.97756057734291"))
                .streetAddress(StreetAddress.of("서울특별시 중구 세종대로353"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.TANGHURU.getFoodName()).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        Store storeOutOfBoundary1 = Store.builder()
                .id(4L)
                .name("경복궁역 4번 출구 분식")
                .storeType(StoreType.FOOD_TRUCK)
                .detailLocation("testDetailLocation3")
                .averageRating(4.2)
                .businessDate("monday,friday,sunday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image3.com")
                .lat(new BigDecimal("37.57603352407639"))
                .lng(new BigDecimal("126.9732441149734"))
                .streetAddress(StreetAddress.of("서울특별시 중구 세종대로77"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.FIRED.getFoodName()).build(),
                                FoodCategory.builder().foodType(FoodType.HOTTEOK.getFoodName()).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        memberRepository.save(member);
        storeRepository.save(storeInBoundary1);
        storeRepository.save(storeInBoundary2);
        storeRepository.save(storeInBoundary3);
        storeRepository.save(storeOutOfBoundary1);
    }

    @Test
    @DisplayName("거리 1km 이내의 오뎅 가게들을 찾는다.")
    public void findStoresByLatLngAndFoodType() {
        // given

        // when

        // then

    }

}