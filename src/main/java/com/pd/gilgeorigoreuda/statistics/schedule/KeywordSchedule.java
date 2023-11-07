package com.pd.gilgeorigoreuda.statistics.schedule;

import com.pd.gilgeorigoreuda.statistics.domain.HotPlace;
import com.pd.gilgeorigoreuda.statistics.repository.HotPlaceRepository;
import com.pd.gilgeorigoreuda.statistics.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Transactional
@RequiredArgsConstructor
public class KeywordSchedule {

    private final KeywordRepository keywordRepository;
    private final HotPlaceRepository hotPlaceRepository;

    @Scheduled(fixedDelay = 3600000)
    public void schedule() {

        Long rank = 1L;

        LocalDateTime startDayTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDayTime = LocalDateTime.now();

        List<Map<String, Long>> top10Keywords = keywordRepository.findTop10Keywords(startDayTime, endDayTime);
        List<HotPlace> hotPlaces = new ArrayList<>();

        for (Map<String, Long> top10Keyword : top10Keywords) {
            String keyword = String.valueOf(top10Keyword.get("keyword"));

            HotPlace hotPlace = HotPlace.builder()
                    .id(rank)
                    .place(keyword)
                    .build();

            hotPlaces.add(hotPlace);

            rank++;
        }

        hotPlaceRepository.saveAll(hotPlaces);

    }
}