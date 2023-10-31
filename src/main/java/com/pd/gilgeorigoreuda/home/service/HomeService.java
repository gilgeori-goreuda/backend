package com.pd.gilgeorigoreuda.home.service;

import com.pd.gilgeorigoreuda.home.dto.response.HotPlaceListResponse;
import com.pd.gilgeorigoreuda.home.dto.response.HotPlaceResponse;
import com.pd.gilgeorigoreuda.home.dto.response.NewPlaceListResponse;
import com.pd.gilgeorigoreuda.home.dto.response.NewPlaceResponse;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final StoreRepository storeRepository;

    @Transactional
    public NewPlaceListResponse getNewPlace(){

        LocalDateTime startDay = LocalDateTime.now().minusDays(7);
        LocalDateTime endDay = LocalDateTime.now();

        List<NewPlaceResponse> newPlaceResponseList = storeRepository.findAllByBetweenDay(startDay, endDay)
                .stream()
                .map(NewPlaceResponse::of)
                .toList();

        return NewPlaceListResponse.of(newPlaceResponseList);
    }

    @Transactional
    public HotPlaceListResponse getHotPlace(){

        List<HotPlaceResponse> hotPlaceResponseList = storeRepository.findStoresByWeightedAverageRating()
                .stream()
                .map(HotPlaceResponse::of)
                .toList();

        return HotPlaceListResponse.of(hotPlaceResponseList);
    }
}
