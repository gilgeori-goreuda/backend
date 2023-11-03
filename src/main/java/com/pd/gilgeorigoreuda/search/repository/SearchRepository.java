package com.pd.gilgeorigoreuda.search.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SearchRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s " +
            "left join fetch s.foodCategories fc " +
            "where POW(s.lat - :lat, 2) + POW(s.lng - :lng, 2) <= :distance " +
            "and (:foodType is null or fc.foodType = :foodType)")
    List<Store> getStoreByAddressAndFoodType(@Param("lat") Double lat,
                                             @Param("lng") Double lng,
                                             @Param("foodType") FoodType foodType,
                                             @Param("distance") Double distance);

}

