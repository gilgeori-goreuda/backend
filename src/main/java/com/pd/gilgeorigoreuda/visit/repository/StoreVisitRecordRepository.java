package com.pd.gilgeorigoreuda.visit.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreVisitRecordRepository extends JpaRepository<StoreVisitRecord, Long> {

    @Query("SELECT svr FROM StoreVisitRecord svr WHERE svr.member.id = :memberId AND svr.store.id = :storeId")
    Optional<StoreVisitRecord> findByMemberIdAndStoreId(final Long memberId, final Long storeId);

}
