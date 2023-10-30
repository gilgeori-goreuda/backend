package com.pd.gilgeorigoreuda.statistics.service;

import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import com.pd.gilgeorigoreuda.statistics.event.KeywordEvent;
import com.pd.gilgeorigoreuda.statistics.repository.KeywordRepository;
import com.pd.gilgeorigoreuda.statistics.schedule.KeywordSchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeywordService {

    private final KeywordRepository keywordRepository;
    private final KeywordSchedule keywordSchedule;

    @Async
    @EventListener
    public void save(KeywordEvent event){
        keywordRepository.save(Keyword
                .builder()
                .keyword(event.getKeyword())
                .build());
    }

    public void getKeyword() {
        keywordSchedule.schedule();
//        for (Map<String, Long> top10Keyword : schedule) {
//            String keyword = String.valueOf(top10Keyword.get("keyword"));
//            Long count = top10Keyword.get("count");
//            System.out.println("Keyword: " + keyword + ", Count: " + count);
//        }
    }
}
