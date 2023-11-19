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
                "and (fc.foodType in :foodTypes or fc is null)")
        List<Store> findStoresByLatLngAndFoodTypes(
                @Param("lat") final BigDecimal lat,
                @Param("lng") final BigDecimal lng,
                @Param("foodTypes") final List<FoodType> foodTypes,
                @Param("distance") final Double distance
        );

        @Query("select s from Store s " +
                "left join fetch s.foodCategories fc " +
                "where POWER(s.lat - :lat, 2) + POWER(s.lng - :lng, 2) <= :distance")
        List<Store> findStoresByLatLngAndFoodTypes22(
                @Param("lat") final BigDecimal lat,
                @Param("lng") final BigDecimal lng,
                @Param("distance") final Double distance
        );

}

