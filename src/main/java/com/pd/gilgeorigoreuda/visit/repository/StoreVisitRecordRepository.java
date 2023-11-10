package com.pd.gilgeorigoreuda.visit.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreVisitRecordRepository extends JpaRepository<StoreVisitRecord, Long> {

    @Query("select svr from StoreVisitRecord svr " +
            "where svr.member.id = :memberId " +
            "and svr.store.id = :storeId")
    Optional<StoreVisitRecord> findByMemberIdAndStoreId(final Long memberId, final Long storeId);

}
