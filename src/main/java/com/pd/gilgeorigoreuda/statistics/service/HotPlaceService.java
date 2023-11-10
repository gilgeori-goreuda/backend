package com.pd.gilgeorigoreuda.statistics.service;

import com.pd.gilgeorigoreuda.statistics.dto.response.HotPlaceListResponse;
import com.pd.gilgeorigoreuda.statistics.dto.response.HotPlaceResponse;
import com.pd.gilgeorigoreuda.statistics.repository.HotPlaceRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HotPlaceService {

    private final HotPlaceRepository hotPlaceRepository;

    public HotPlaceListResponse getHotPlaces() {
        List<HotPlaceResponse> hotPlaceResponse = hotPlaceRepository.findAll()
                .stream()
                .map(HotPlaceResponse::of)
                .toList();

        return HotPlaceListResponse.of(hotPlaceResponse);
    }

}
