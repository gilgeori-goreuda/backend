package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("SELECT s " +
            "FROM Store s " +
            "where s.createdAt >= :startDay and s.createdAt <= :endDay")
    List<Store> findAllByBetweenDay(@Param("startDay") LocalDateTime startDay,
                                    @Param("endDay") LocalDateTime endDay);

    @Query("SELECT s " +
            "FROM Store s " +
            "ORDER BY ((0.713 * (s.averageRating / 5)) + (0.287 * (s.totalVisitCount * 0.01))) " +
            "DESC " +
            "limit 10")

    List<Store> findStoresByWeightedAverageRating();
}