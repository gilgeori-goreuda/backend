package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreReportHistoryRepository extends JpaRepository<StoreReportHistory, Long> {


	@Query("SELECT sr from StoreReportHistory sr LEFT JOIN FETCH sr.member m WHERE sr.id = :reportId")
	Optional<StoreReportHistory> findStoreReportHistoryByReportId(@Param("reportId") final Long reportId);

	@Query("SELECT sr "
			+ "FROM StoreReportHistory sr "
			+ "LEFT JOIN FETCH sr.member m "
			+ "LEFT JOIN FETCH sr.store s "
			+ "WHERE sr.member.id = :memberId")
	List<StoreReportHistory> findStoreReportHistoriesByMemberId(@Param("memberId") final Long memberId);

	@Query("SELECT sr "
			+ "FROM StoreReportHistory sr "
			+ "LEFT JOIN FETCH sr.store s "
			+ "LEFT JOIN FETCH sr.member m "
			+ "WHERE sr.store.id = :storeId ")
	List<StoreReportHistory> findStoreReportHistoriesByStoreId(@Param("storeId") final Long storeId);

	@Query("SELECT sr "
			+ "FROM StoreReportHistory sr "
			+ "WHERE sr.store.id = :storeId "
			+ "AND sr.member.id = :memberId"
	)
	Optional<StoreReportHistory> findStoreReportHistoryByStoreIdAndMemberId(@Param("storeId") final Long storeId, @Param("memberId") final Long memberId);

}
