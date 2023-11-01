package com.pd.gilgeorigoreuda.search.service;

import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchListResponse;
import com.pd.gilgeorigoreuda.search.dto.response.AddressSearchResponse;
import com.pd.gilgeorigoreuda.search.repository.SearchRepository;
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

    public AddressSearchListResponse getStoreByAddress(Double lat, Double lng){
        List<AddressSearchResponse> results = searchRepository.findStoreByAddress(lat, lng)
                                                .stream()
                                                .map(AddressSearchResponse::new)
                                                .toList();

        return AddressSearchListResponse.of(results);
    }

}
