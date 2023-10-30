package com.pd.gilgeorigoreuda.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query("select s "
		+ "from Store s "
		+ "left join fetch s.member m "
		+ "left join fetch s.foodCategories fc "
		+ "where s.id = :storeId")
	Store findByStoreId(@Param("storeId") Long storeId);

}
