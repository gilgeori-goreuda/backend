package com.pd.gilgeorigoreuda.statistics.schedule;

import com.pd.gilgeorigoreuda.statistics.domain.HotPlace;
import com.pd.gilgeorigoreuda.statistics.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class KeywordSchedule {

    private final KeywordRepository keywordRepository;

    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void schedule(){
        LocalDateTime startDayTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDayTime = LocalDateTime.now();

        List<Map<String, Long>> top10Keywords = keywordRepository.findTop10Keywords(startDayTime, endDayTime);

        for (Map<String, Long> top10Keyword : top10Keywords) {
            String keyword = String.valueOf(top10Keyword.get("keyword"));
            HotPlace hotPlace = HotPlace.builder().hotPlace(keyword).build();

        }
    }
}