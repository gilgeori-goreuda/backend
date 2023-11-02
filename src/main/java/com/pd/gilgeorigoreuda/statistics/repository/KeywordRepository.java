package com.pd.gilgeorigoreuda.statistics.repository;

import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {

    @Query(value = "SELECT keyword, COUNT(*) as count " +
            "FROM (SELECT keyword, created_at From Keywords WHERE created_at >= :startDayTime AND created_at <= :endDayTime) as finltered_data " +
            "GROUP BY keyword, DATE(created_at), HOUR(created_at) " +
            "ORDER BY count DESC, keyword ASC " +
            "LIMIT 10", nativeQuery = true)
    List<Map<String, Long>> findTop10Keywords(@Param("startDayTime") LocalDateTime startDayTime, @Param("endDayTime") LocalDateTime endDayTime);
}
