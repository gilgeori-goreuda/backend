package com.pd.gilgeorigoreuda.statistics.service;

import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import com.pd.gilgeorigoreuda.statistics.event.KeywordEvent;
import com.pd.gilgeorigoreuda.statistics.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KeywordService {
    HashMap<String, Long> keywords = new HashMap<String, Long>();
    private final KeywordRepository keywordRepository;
    @Async
    @EventListener
    public void save(KeywordEvent event){
        keywordRepository.save(Keyword
                .builder()
                .keyword(event.getKeyword())
                .build());
    }

    public void getKeyword() {
        List<Object[]> top10Keywords = keywordRepository.findTop10Keywords();

        for (Object[] row : top10Keywords) {
            String keyword = (String) row[0];
            Long count = (Long) row[1];
            keywords.put(keyword, count);
        }
    }
}
