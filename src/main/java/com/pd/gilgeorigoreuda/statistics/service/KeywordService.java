package com.pd.gilgeorigoreuda.statistics.service;

import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import com.pd.gilgeorigoreuda.statistics.event.KeywordEvent;
import com.pd.gilgeorigoreuda.statistics.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Async
    @EventListener
    public void save(KeywordEvent event){
        keywordRepository.save(Keyword
                .builder()
                .keyword(event.getKeyword())
                .build());
    }

}
