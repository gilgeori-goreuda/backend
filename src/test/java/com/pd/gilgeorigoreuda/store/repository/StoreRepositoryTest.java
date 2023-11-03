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
	private StoreNativeQueryRepository storeNativeQueryRepository;

	@Test
	void test() {
		Optional<Long> alreadyExistInBoundary = storeNativeQueryRepository.isAlreadyExistInBoundary(37.54006651056828,
			127.0691658275643, "123", "마포구", 10);

		System.out.println(alreadyExistInBoundary.get());

	}

}