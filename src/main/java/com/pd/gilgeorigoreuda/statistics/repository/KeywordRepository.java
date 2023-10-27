package com.pd.gilgeorigoreuda.statistics.repository;

import com.pd.gilgeorigoreuda.statistics.domain.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword,Long> {

    @Query(value = "SELECT keyword, COUNT(*) as count FROM Keywords GROUP BY keyword ORDER BY count DESC LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10Keywords();
}
