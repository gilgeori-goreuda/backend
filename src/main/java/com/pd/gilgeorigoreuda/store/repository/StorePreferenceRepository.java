package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StorePreferenceRepository extends JpaRepository<StorePreference, Long> {

    @Query("select p from StorePreference p " +
            "left join fetch p.store s " +
            "left join fetch p.member m " +
            "left join fetch p.store.foodCategories fc " +
            "where p.member.id = :memberId and p.preferenceType = 'PREFERRED'")
    List<StorePreference> findMyPreferredStores(@Param("memberId") Long memberId);

}
