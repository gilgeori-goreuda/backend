package com.pd.gilgeorigoreuda.search.controller;

import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreListResponse;
import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreResponse;
import com.pd.gilgeorigoreuda.search.service.SearchService;
import com.pd.gilgeorigoreuda.settings.ControllerTest;
import com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.BDDMockito.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.assertj.core.api.Assertions.*;

@WebMvcTest(SearchController.class)
@AutoConfigureRestDocs
class SearchControllerTest extends ControllerTest {

    @MockBean
    private SearchService searchService;

    private ResultActions performSearchRequest(final String mLat, final String mLng, final String rLat, final String rLng, final String foodType) throws Exception {
        return mockMvc.perform(
                get("/api/v1/search/address")
                        .param("mLat", mLat)
                        .param("mLng",mLng)
                        .param("rLat", rLat)
                        .param("rLng", rLng)
                        .param("foodType", foodType)
                        .contentType(APPLICATION_JSON)
        );
    }

    private SearchStoreListResponse getSearchStoreListResponse() {
        return SearchStoreListResponse.of(
                List.of(
                        SearchStoreResponse.of(
                                StoreFixtures.TACOYAKI(),
                                10
                        ),
                        SearchStoreResponse.of(
                                StoreFixtures.TANGHURU(),
                                20
                        ),
                        SearchStoreResponse.of(
                                StoreFixtures.TTEOKBOKKI(),
                                30
                        )
                )
        );
    }

    @Test
    @DisplayName("주변 가게 검색 테스트")
    void searchByLatLngAndFoodCategories() throws Exception {
        // given
        SearchStoreListResponse searchStoreListResponse = getSearchStoreListResponse();

        given(searchService.searchByLatLngAndFoodCategories(any(), any(), any(), any(), any()))
                .willReturn(searchStoreListResponse);

        // when
        ResultActions resultActions = performSearchRequest(any(), any(), any(), any(), any());

        // then
        MvcResult mvcResult = resultActions
                .andExpect(status().isOk())
                .andDo(
                        restDocs.document(
                                queryParameters(
                                        parameterWithName("mLat").description("현재 사용자 위치 위도"),
                                        parameterWithName("mLng").description("현재 사용자 위치 경도"),
                                        parameterWithName("rLat").description("검색 반경 위도"),
                                        parameterWithName("rLng").description("검색 반경 경도"),
                                        parameterWithName("foodType").description("음식 타입")
                                ),
                                responseFields(
                                        fieldWithPath("results").description("검색 결과 목록"),
                                        fieldWithPath("results[].id").description("가게 ID"),
                                        fieldWithPath("results[].name").description("가게 이름"),
                                        fieldWithPath("results[].storeType").description("가게 타입"),
                                        fieldWithPath("results[].detailLocation").description("가게 상세 위치"),
                                        fieldWithPath("results[].averageRating").description("가게 평균 평점"),
                                        fieldWithPath("results[].businessDate").description("가게 영업 요일"),
                                        fieldWithPath("results[].imageUrl").description("가게 이미지 URL"),
                                        fieldWithPath("results[].lat").description("가게 위도"),
                                        fieldWithPath("results[].lng").description("가게 경도"),
                                        fieldWithPath("results[].streetAddress").description("가게 주소"),
                                        fieldWithPath("results[].distanceFromStore").description("가게까지의 거리"),
                                        fieldWithPath("results[].storeCategories").description("가게의 카테고리 정보"),
                                        fieldWithPath("results[].storeCategories.categories").description("가게 음식 카테고리 목록"),
                                        fieldWithPath("results[].storeCategories.categories[].id").description("음식 카테고리 ID"),
                                        fieldWithPath("results[].storeCategories.categories[].foodType").description("음식 타입")
                                )
                        )
                ).andReturn();

        SearchStoreListResponse response = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                SearchStoreListResponse.class
        );

        assertThat(response).usingRecursiveComparison()
                .isEqualTo(searchStoreListResponse);

        then(searchService).should(times(1))
                .searchByLatLngAndFoodCategories(any(), any(), any(), any(), any());
    }

}