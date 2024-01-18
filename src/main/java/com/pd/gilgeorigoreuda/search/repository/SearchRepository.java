package com.pd.gilgeorigoreuda.search.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SearchRepository extends JpaRepository<Store, Long> {

        @Query("SELECT s FROM Store s " +
                "LEFT JOIN FETCH s.foodCategories fc " +
                "WHERE FUNCTION('acos', FUNCTION('cos', FUNCTION('radians', :lat)) * FUNCTION('cos', FUNCTION('radians', s.lat)) * FUNCTION('cos', FUNCTION('radians', :lng) - FUNCTION('radians', s.lng)) + FUNCTION('sin', FUNCTION('radians', :lat)) * FUNCTION('sin', FUNCTION('radians', s.lat))) " +
                "* 6371 * 1000 <= :boundary " +
                "AND (:foodType IS NULL OR fc.foodType = :foodType)"
        )
        List<Store> searchStoresByLatLngAndFoodType(
                @Param("lat") final BigDecimal lat,
                @Param("lng") final BigDecimal lng,
                @Param("foodType") final String foodType,
                @Param("boundary") final Integer boundary
        );

}

