package com.pd.gilgeorigoreuda.statistics.repository;

import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    @Query(value =
            "select keyword, count(*) as count " +
            "from " +
            "(select keyword, created_at " +
                "from Keywords " +
                "where created_at >= :startDayTime " +
                "and created_at <= :endDayTime) as finltered_data " +
            "group by keyword, DATE(created_at), HOUR(created_at) " +
            "order by count desc, keyword asc " +
            "limit 10",
            nativeQuery = true)
    List<Map<String, Long>> findTop10Keywords(@Param("startDayTime") final LocalDateTime startDayTime,
                                              @Param("endDayTime") final LocalDateTime endDayTime);

}
