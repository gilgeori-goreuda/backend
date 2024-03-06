package com.pd.gilgeorigoreuda.search.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SearchRepository extends JpaRepository<Store, Long> {

        @Query(
                """
                SELECT s FROM Store s \s
                LEFT JOIN FETCH s.foodCategories fc \s
                WHERE \s
                    FUNCTION('acos', \s
                        FUNCTION('cos', FUNCTION('radians', :lat)) * FUNCTION('cos', FUNCTION('radians', s.lat)) * \s
                        FUNCTION('cos', FUNCTION('radians', :lng) - FUNCTION('radians', s.lng)) + \s
                        FUNCTION('sin', FUNCTION('radians', :lat)) * FUNCTION('sin', FUNCTION('radians', s.lat))\s
                    ) * 6371 * 1000 <= :boundary \s
                    AND (:foodType IS NULL OR fc.foodType = :foodType) \s
                """
        )
        List<Store> searchStoresByLatLngAndFoodType(
                @Param("lat") final BigDecimal lat,
                @Param("lng") final BigDecimal lng,
                @Param("foodType") final String foodType,
                @Param("boundary") final Integer boundary
        );

}

