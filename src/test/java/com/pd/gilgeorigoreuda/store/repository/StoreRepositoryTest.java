package com.pd.gilgeorigoreuda.store.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;

@SpringBootTest
class StoreRepositoryTest {

	@Autowired
	private StoreRepository storeRepository;

	@Test
	void test() {
		Optional<Store> byStoreId = storeRepository.findByStoreId(1L);
		byStoreId.ifPresent(store -> {
			System.out.println(store.getFoodCategories().size());
		});
	}

}