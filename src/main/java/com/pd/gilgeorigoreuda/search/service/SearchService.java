package com.pd.gilgeorigoreuda.search.service;

import com.pd.gilgeorigoreuda.common.util.DistanceCalculator;
import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreListResponse;
import com.pd.gilgeorigoreuda.search.dto.response.SearchStoreResponse;
import com.pd.gilgeorigoreuda.search.repository.SearchRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;

    private static final Double DISTANCE_1KM =0.00012754530697130809;

    public SearchStoreListResponse searchByLatLngAndFoodCategories(
            final BigDecimal memberLat,
            final BigDecimal memberLng,
            final BigDecimal referenceLat,
            final BigDecimal referenceLng,
            final String foodType
    ) {
        List<SearchStoreResponse> searchStoreResponse = searchRepository
                .findStoresByLatLngAndFoodTypes(referenceLat, referenceLng, foodType, DISTANCE_1KM)
                .stream()
                .map(store -> mapToAddressSearchResponse(memberLat, memberLng, store))
                .toList();

        return SearchStoreListResponse.of(searchStoreResponse);
    }

    private SearchStoreResponse mapToAddressSearchResponse(
            final BigDecimal memberLat,
            final BigDecimal memberLng,
            final Store store
    ) {
        BigDecimal targetStoreLat = store.getLat();
        BigDecimal targetStoreLng = store.getLng();
        Integer distanceFromStore = getDistanceBetweenStoreAndMember(memberLat, memberLng, targetStoreLat, targetStoreLng);

        return SearchStoreResponse.of(store, distanceFromStore);
    }

    private int getDistanceBetweenStoreAndMember(
            final BigDecimal memberLat,
            final BigDecimal memberLng,
            final BigDecimal targetStoreLat,
            final BigDecimal targetStoreLng
    ) {
        return DistanceCalculator.calculateDistance(memberLat, memberLng, targetStoreLat, targetStoreLng);
    }

}
