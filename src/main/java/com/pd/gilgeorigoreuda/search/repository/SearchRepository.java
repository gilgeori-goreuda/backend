package com.pd.gilgeorigoreuda.search.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface SearchRepository extends JpaRepository<Store, Long> {

        @Query("select s from Store s " +
                "left join fetch s.foodCategories fc " +
                "where POWER(s.lat - :lat, 2) + POWER(s.lng - :lng, 2) <= :distance " +
                "and (:foodType is null or fc.foodType = :foodType)")
        List<Store> findStoresByLatLngAndFoodTypes(
                @Param("lat") final BigDecimal lat,
                @Param("lng") final BigDecimal lng,
                @Param("foodType") final FoodType foodType,
                @Param("distance") final Double distance
        );

}

