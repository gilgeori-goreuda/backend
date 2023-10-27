package com.pd.gilgeorigoreuda.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;

public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query()
	StoreResponse findByStoreId(Long storeId);

}
