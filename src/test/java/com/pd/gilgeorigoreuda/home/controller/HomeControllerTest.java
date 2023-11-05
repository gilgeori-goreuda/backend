package com.pd.gilgeorigoreuda.home.controller;

import com.pd.gilgeorigoreuda.home.dto.response.PlaceListResponse;
import com.pd.gilgeorigoreuda.home.dto.response.PlaceResponse;
import com.pd.gilgeorigoreuda.home.service.HomeService;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StreetAddress;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

class HomeControllerTest extends ControllerTest {

    @MockBean
    private HomeService homeService;

    private List<Store> getMockStores() {
        List<Store> stores = new ArrayList<>();

        Store store1 = Store.builder()
                .id(1L)
                .imageUrl("https://image.com")
                .streetAddress(StreetAddress.of("서울시 마포구 독막로 324"))
                .detailLocation("어쩌구 저쩌구")
                .name("store1")
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .averageRating(4.5)
                .totalVisitCount(50)
                .build();

        Store store2 = Store.builder()
                .id(2L)
                .imageUrl("https://image2.com")
                .streetAddress(StreetAddress.of("서울시 광진구 독막로 324"))
                .detailLocation("어쩌구 저쩌구")
                .name("store1")
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .name("store2")
                .averageRating(3.5)
                .totalVisitCount(10)
                .build();

        Store store3 = Store.builder()
                .id(3L)
                .imageUrl("https://image.com")
                .streetAddress(StreetAddress.of("서울시 강남구 독막로 324"))
                .detailLocation("어쩌구 저쩌구")
                .name("store1")
                .openTime(LocalTime.of(9, 0))
                .closeTime(LocalTime.of(18, 0))
                .name("store3")
                .averageRating(4.7)
                .totalVisitCount(25)
                .build();

        stores.add(store1);
        stores.add(store2);
        stores.add(store3);

        return stores;
    }

    private PlaceListResponse getMockStoreList() {
        return PlaceListResponse.of(
                getMockStores()
                        .stream()
                        .map(PlaceResponse::of)
                        .toList()
        );
    }

    @Test
    @DisplayName("신규 장소 추천")
    void newPlaceSuccess() throws Exception {

        PlaceListResponse placeListResponse = getMockStoreList();

        Mockito.when(homeService.getNewPlace())
                .thenReturn(placeListResponse);


        ResultActions response = mockMvc
                .perform(get("/api/v1/home/newplace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendPlace", hasSize(3)))
                .andExpect(jsonPath("$.recommendPlace[0].id", comparesEqualTo(1)))
                .andExpect(jsonPath("$.recommendPlace[0].name", comparesEqualTo("store1")))
                .andExpect(content().json(objectMapper.writeValueAsString(placeListResponse)))
                .andDo(print())
        ;
    }

    @Test
    @DisplayName("인기 장소 추천 성공")
    void hotPlaceSuccess() throws Exception{

        PlaceListResponse placeListResponse = getMockStoreList();

        Mockito.when(homeService.getHotPlace())
                .thenReturn(placeListResponse);

        ResultActions response = mockMvc
                .perform(get("/api/v1/home/hotplace")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendPlace", hasSize(3)))
                .andExpect(content().json(objectMapper.writeValueAsString(placeListResponse)))
                .andDo(print())
        ;
    }

}