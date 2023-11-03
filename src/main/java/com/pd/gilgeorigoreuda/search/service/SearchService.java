package com.pd.gilgeorigoreuda.search.service;

import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchListResponse;
import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchResponse;
import com.pd.gilgeorigoreuda.search.repository.SearchRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final SearchRepository searchRepository;
    private static final Double DISTANCE_1KM =0.00012754530697130809;

    public AddressSearchListResponse getStoreByAddressAndFoodType(Double lat, Double lng, FoodType foodType){
        List<AddressSearchResponse> results = searchRepository.getStoreByAddressAndFoodType(lat, lng, foodType,DISTANCE_1KM)
                                                .stream()
                                                .map(AddressSearchResponse::new)
                                                .toList();

        return AddressSearchListResponse.of(results);
    }

}
