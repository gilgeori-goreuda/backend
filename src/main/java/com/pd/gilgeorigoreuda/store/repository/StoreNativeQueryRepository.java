package com.pd.gilgeorigoreuda.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;

public interface StoreNativeQueryRepository extends JpaRepository<Store, Long> {

	// todo: 데이터 스캔 범위를 줄이기 위해 FROM절에 서브 쿼리로 도로명 주소로 한번 필터링을 해면 더 좋을까?
	// todo: 주소를 대,중,소로 나눠서 검색하면 더 좋을까?

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
	Optional<Long> isAlreadyExistInBoundary(@Param("lat") Double lat, @Param("lng") Double lng, @Param("largeAddress") String largeAddress, @Param("mediumAddress") String mediumAddress, @Param("boundary") Integer boundary);

}
