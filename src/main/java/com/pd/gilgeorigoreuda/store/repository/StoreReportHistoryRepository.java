package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreReportHistoryRepository extends JpaRepository<StoreReportHistory, Long> {

	@Query("select s "
		+ "from Store s "
		+ "left join fetch s.member m "
		+ "left join fetch s.foodCategories fc "
		+ "where s.id = :storeId")
	Optional<Store> findStoreWithMemberAndCategories(@Param("storeId") Long storeId);

}
