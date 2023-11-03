package com.pd.gilgeorigoreuda.home.controller;

import com.pd.gilgeorigoreuda.home.dto.response.PlaceListResponse;
import com.pd.gilgeorigoreuda.home.dto.response.PlaceResponse;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

class HomeControllerTest extends ControllerTest {

    private List<Store> getMockStores() {
        List<Store> stores = new ArrayList<>();

        Store store1 = Store.builder()
                .id(1L)
                .name("store1")
                .averageRating(4.5)
                .totalVisitCount(50)
                .build();

        Store store2 = Store.builder()
                .id(2L)
                .name("store2")
                .averageRating(3.5)
                .totalVisitCount(10)
                .build();

        Store store3 = Store.builder()
                .id(3L)
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