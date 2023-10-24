package com.pd.gilgeorigoreuda.store.service;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.statistics.event.KeywordEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ApplicationEventPublisher publisher;

    public void getStore(String keyword){
        publisher.publishEvent(new KeywordEvent(keyword));   //이벤트 발행
    }
}
