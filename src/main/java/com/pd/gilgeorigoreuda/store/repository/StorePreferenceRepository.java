package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StorePreferenceRepository extends JpaRepository<StorePreference, Long> {

    @Query(value = "select * from store_preferences where store_id = :storeId and member_id = :memberId", nativeQuery = true)
    Optional<StorePreference> findByStoreIdAndMemberId(@Param("storeId") Long storeId, @Param("memberId") Long memberId);
}
