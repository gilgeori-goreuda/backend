package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StorePreferenceRepository extends JpaRepository<StorePreference, Long> {

    @Query("SELECT p FROM StorePreference p " +
            "LEFT JOIN FETCH p.store s " +
            "LEFT JOIN FETCH p.member m " +
            "LEFT JOIN FETCH p.store.foodCategories fc " +
            "WHERE p.member.id = :memberId AND p.preferenceType = 'PREFERRED'")
    List<StorePreference> findMyPreferredStores(@Param("memberId") final Long memberId);

    @Query("SELECT p FROM StorePreference p WHERE p.store.id = :storeId AND p.member.id = :memberId")
    Optional<StorePreference> findByStoreIdAndMemberId(@Param("storeId") final Long storeId, @Param("memberId") final Long memberId);

}
