package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("select s from StorePreference p " +
            "left join p.store s " +
            "where p.member.id = :memberId and p.preferenceType = 'PREFERRED'")
    List<Store> findAllByMemberIdAndLike(@Param("memberId") Long memberId);
}
