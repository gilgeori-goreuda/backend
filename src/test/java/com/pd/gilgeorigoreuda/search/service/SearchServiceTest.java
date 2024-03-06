package com.pd.gilgeorigoreuda.search.service;

import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreListResponse;
import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreResponse;
import com.pd.gilgeorigoreuda.settings.ServiceTest;
import com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;
import static org.mockito.BDDMockito.*;

class SearchServiceTest extends ServiceTest {

    private static final Integer BOUNDARY_1KM = 1000;

    private static final BigDecimal MEMBER_LAT = new BigDecimal("37.49732853932101");
    private static final BigDecimal MEMBER_LNG = new BigDecimal("127.02821485508554");
    private static final BigDecimal REFERENCE_LAT = new BigDecimal("37.49732853932101");
    private static final BigDecimal REFERENCE_LNG = new BigDecimal("127.02821485508554");
    private static final String FOOD_TYPE = FoodType.of("붕어빵").getFoodName();

    @Test
    @DisplayName("사용자의 위치 기준 반경 1KM 내의 음식점 목록을 조회")
    void searchByLatLngAndFoodCategories() {
        // given
        given(
                searchRepository.searchStoresByLatLngAndFoodType(
                        any(BigDecimal.class),
                        any(BigDecimal.class),
                        anyString(),
                        any(Integer.class)
                )
        ).willReturn(List.of(BUNGEOPPANG(), ODENG(), TTEOKBOKKI()));

        // when
        SearchStoreListResponse searchStoreListResponse = searchService.searchByLatLngAndFoodCategories(
                MEMBER_LAT,
                MEMBER_LNG,
                REFERENCE_LAT,
                REFERENCE_LNG,
                FOOD_TYPE
        );

        // then
        assertSoftly(
                softly -> {
                    assertThat(searchStoreListResponse.getResults()).hasSize(3);
                    then(searchRepository).should(times(1)).searchStoresByLatLngAndFoodType(
                            any(BigDecimal.class),
                            any(BigDecimal.class),
                            anyString(),
                            any(Integer.class)
                    );

                    then(distanceCalculator).should(times(3)).getDistance(
                            any(BigDecimal.class),
                            any(BigDecimal.class),
                            any(BigDecimal.class),
                            any(BigDecimal.class)
                    );
                }
        );
    }

}