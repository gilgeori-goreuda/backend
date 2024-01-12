package com.pd.gilgeorigoreuda.store.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;

public interface StoreNativeQueryRepository extends JpaRepository<Store, Long> {

	@Query(value =
			"SELECT s.id "
					+ "FROM stores s "
					+ "WHERE "
					+ 	"s.large_address = :largeAddress "
					+   "AND "
					+   "s.medium_address = :mediumAddress "
					+ 	"AND "
					+ 	"FLOOR(6371 * 1000 * acos("
					+ 		"cos(radians(:lat)) * cos(radians(s.lat)) * cos(radians(:lng) - radians(s.lng)) +"
					+ 		"sin(radians(:lat)) * sin(radians(s.lat))"
					+ 	")"
					+ ") <= :boundary "
					+ "LIMIT 1",
			nativeQuery = true)
	Optional<Long> isAlreadyExistInBoundary(
			@Param("lat") final BigDecimal lat,
			@Param("lng") final BigDecimal lng,
			@Param("largeAddress") final String largeAddress,
			@Param("mediumAddress") final String mediumAddress,
			@Param("boundary") final Integer boundary
	);

}
