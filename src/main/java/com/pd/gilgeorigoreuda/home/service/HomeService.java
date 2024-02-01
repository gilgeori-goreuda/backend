package com.pd.gilgeorigoreuda.home.service;

import com.pd.gilgeorigoreuda.home.dto.response.PlaceListResponse;
import com.pd.gilgeorigoreuda.home.dto.response.PlaceResponse;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final StoreRepository storeRepository;

    public PlaceListResponse getNewPlace() {
        LocalDateTime startDay = LocalDateTime.now().minusDays(7);
        LocalDateTime endDay = LocalDateTime.now();

        List<PlaceResponse> placeResponseList = storeRepository.findStoresByBetweenDay(startDay, endDay)
                .stream()
                .map(PlaceResponse::of)
                .toList();

        return PlaceListResponse.of(placeResponseList);
    }

    public PlaceListResponse getHotPlace() {
        List<PlaceResponse> placeResponseList = storeRepository.findStoresByRateAndVisitCount()
                .stream()
                .map(PlaceResponse::of)
                .toList();

        return PlaceListResponse.of(placeResponseList);
    }

}
