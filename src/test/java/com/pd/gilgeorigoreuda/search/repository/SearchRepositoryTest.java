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
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SearchRepositoryTest extends RepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private MemberRepository memberRepository;

    private static final BigDecimal REFERENCE_LAT = new BigDecimal("37.53977925497371");
    private static final BigDecimal REFERENCE_LNG = new BigDecimal("127.0711654823082");
    private static final Double DISTANCE_1KM = 0.00012754530697130809;

    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .id(1L)
                .nickname("nickname")
                .socialId("socialId")
                .build();

        Store storeInDistance1 = Store.builder()
                .id(1L)
                .name("testStore1")
                .storeType(StoreType.FOOD_STALL)
                .detailLocation("testDetailLocation1")
                .averageRating(4.5)
                .businessDate("monday,friday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image.com")
                .lat(new BigDecimal("37.5399777941172"))
                .lng(new BigDecimal("127.07063386623555"))
                .streetAddress(StreetAddress.of("서울특별시 광진구 능동로22"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.ODENG).build(),
                                FoodCategory.builder().foodType(FoodType.BUNGEOPPANG).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        Store storeInDistance2 = Store.builder()
                .id(2L)
                .name("testStore2")
                .storeType(StoreType.FOOD_TRUCK)
                .detailLocation("testDetailLocation2")
                .averageRating(4.0)
                .businessDate("monday,sunday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image2.com")
                .lat(new BigDecimal("37.54035666149883"))
                .lng(new BigDecimal("127.06988177265133"))
                .streetAddress(StreetAddress.of("서울특별시 광진구 능동로123"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.ODENG).build(),
                                FoodCategory.builder().foodType(FoodType.EGGBREAD).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        Store storeOutOfDistance1 = Store.builder()
                .id(3L)
                .name("testStore3")
                .storeType(StoreType.FOOD_TRUCK)
                .detailLocation("testDetailLocation3")
                .averageRating(4.2)
                .businessDate("monday,sunday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("http://image3.com")
                .lat(new BigDecimal("37.55734847509708"))
                .lng(new BigDecimal("127.02947109103378"))
                .streetAddress(StreetAddress.of("서울특별시 성동구 행당로77"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(member)
                .foodCategories(
                        Arrays.asList(
                                FoodCategory.builder().foodType(FoodType.FIRED).build(),
                                FoodCategory.builder().foodType(FoodType.HOTTEOK).build()
                        )
                )
                .totalReportCount(0)
                .isBlocked(false)
                .build();

        memberRepository.save(member);
        storeRepository.save(storeInDistance1);
        storeRepository.save(storeInDistance2);
        storeRepository.save(storeOutOfDistance1);
    }

    @Test
    @DisplayName("거리 1km 이내의 가게들을 찾는다.")
    public void findStoresByLatLngAndFoodTypes() {
        // given
        List<FoodType> foodTypes = Arrays.asList(FoodType.ODENG, FoodType.BUNGEOPPANG);

        // when
        List<Store> result = searchRepository.findStoresByLatLngAndFoodTypes(REFERENCE_LAT, REFERENCE_LNG, foodTypes, DISTANCE_1KM);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("testStore1");
        assertThat(result.get(0).getFoodCategories()).hasSize(2);
        assertThat(result.get(0).getFoodCategories().get(0).getFoodType()).isEqualTo(FoodType.ODENG);
        assertThat(result.get(0).getFoodCategories().get(1).getFoodType()).isEqualTo(FoodType.BUNGEOPPANG);
        assertThat(result.get(0).getStreetAddress().getLargeAddress()).isEqualTo("서울특별시");
        assertThat(result.get(0).getStreetAddress().getMediumAddress()).isEqualTo("광진구");
        assertThat(result.get(0).getStreetAddress().getSmallAddress()).isEqualTo("능동로22");
    }

}