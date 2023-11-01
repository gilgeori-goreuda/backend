package com.pd.gilgeorigoreuda.search.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SearchRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s " +
            "left join fetch s.categories " +
        "where POW(s.lat - :lat, 2) + POW(s.lng - :lng, 2) <= 0.00012754530697130809 ")
    List<Store> findStoreByAddress(@Param("lat") Double lat, @Param("lng") Double lng);

}
