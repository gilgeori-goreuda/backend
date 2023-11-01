package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional


public class SearchService {

    public List<Store> getStoreByAddress(Double lat, Double lng){
        
        return
    }
}
