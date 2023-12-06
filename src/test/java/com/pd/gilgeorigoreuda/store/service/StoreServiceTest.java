package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.image.service.ImageService;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.store.domain.entity.*;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

class StoreServiceTest extends ServiceTest {

    private final Integer TEST_BOUNDARY = 10;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreNativeQueryRepository storeNativeQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ImageService imageService;

    private Store makeStore() {
        return Store.builder()
                .name("붕어빵 가게")
                .storeType(StoreType.of("포장마차"))
                .detailLocation("강남역 1번 출구 앞")
                .businessDate("monday,tuesday,wednesday,thursday,friday,saturday,sunday")
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(23, 0))
                .purchaseType(PurchaseType.of("현금"))
                .imageUrl("https://image.com")
                .lat(BigDecimal.valueOf(37.123456))
                .lng(BigDecimal.valueOf(127.123456))
                .streetAddress(StreetAddress.of("서울특별시 강남구 언주로1"))
                .lastModifiedMemberNickname(null)
                .member(
                        Member.builder()
                                .id(1L)
                                .nickname("test")
                                .build()
                )
                .foodCategories(
                        List.of(
                                FoodCategory.builder()
                                        .id(1L)
                                        .foodType(FoodType.of("붕어빵"))
                                        .build(),
                                FoodCategory.builder()
                                        .id(2L)
                                        .foodType(FoodType.of("호떡"))
                                        .build()
                        )
                )
                .build();
    }

    @Test
    @DisplayName("해당 위경도 기준 반경 10M 범위안에 가게가 없으면 저장한다.")
    void saveStore() {
        // given

        // when

        // then
    }

}