package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.store.domain.entity.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.pd.gilgeorigoreuda.settings.fixtures.FoodCategoryFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.store.domain.entity.StoreType.*;

public class StoreFixtures {

    public static Store BUNGEOPPANG() {
        return Store.builder()
                .id(1L)
                .name("강남역 2번 출구 붕어빵 가게")
                .storeType(FOOD_STALL)
                .detailLocation("강남역 2번 출구 10M 앞")
                .averageRating(4.5)
                .businessDate("monday,tuesday,wednesday,thursday,friday,saturday,sunday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("https://image.com")
                .lat(new BigDecimal("37.49732853932101"))
                .lng(new BigDecimal("127.02821485508554"))
                .streetAddress(StreetAddress.of("서울특별시 강남구 강남대로 396"))
                .totalVisitCount(100)
                .lastModifiedMemberNickname("nickname1")
                .member(KIM())
                .foodCategories(new ArrayList<>(List.of(BUNGEOPPANG_CATEGORY(), EGGBREAD_CATEGORY())))
                .totalReportCount(0)
                .isBlocked(false)
                .build();
    }

    public static Store ODENG() {
        return Store.builder()
                .id(2L)
                .name("강남역 3번 출구 오뎅 가게")
                .storeType(FOOD_TRUCK)
                .detailLocation("강남역 3번 출구 10M 앞")
                .averageRating(4.0)
                .businessDate("monday,tuesday,wednesday,thursday,friday,saturday")
                .openTime(LocalTime.of(10, 0))
                .closeTime(LocalTime.of(20, 0))
                .purchaseType(PurchaseType.CASH)
                .imageUrl("https://image2.com")
                .lat(new BigDecimal("37.49677886297113"))
                .lng(new BigDecimal("127.02847474323126"))
                .streetAddress(StreetAddress.of("서울특별시 강남구 강남대로 453"))
                .totalVisitCount(50)
                .lastModifiedMemberNickname("nickname1")
                .member(LEE())
                .foodCategories(new ArrayList<>(List.of(ODENG_CATEGORY(), TTEOKBOKKI_CATEGORY())))
                .totalReportCount(0)
                .isBlocked(false)
                .build();
    }

}
