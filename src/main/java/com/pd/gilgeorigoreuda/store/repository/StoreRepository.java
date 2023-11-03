package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import java.time.LocalDateTime;

public interface StoreRepository extends JpaRepository<Store, Long> {

	@Query("select s "
		+ "from Store s "
		+ "left join fetch s.member m "
		+ "left join fetch s.foodCategories fc "
		+ "where s.id = :storeId")
	Optional<Store> findStoreWithMemberAndCategories(@Param("storeId") Long storeId);

	@Query("select s "
		+ "from Store s "
		+ "left join fetch s.member m "
		+ "where s.id = :storeId")
	Optional<Store> findStoreWithMember(@Param("storeId") Long storeId);

	@Query("select s " +
		"from Store s " +
		"where s.createdAt >= :startDay and s.createdAt <= :endDay " +
		"order by s.createdAt desc " +
		"limit 10")
	List<Store> findAllByBetweenDay(@Param("startDay") LocalDateTime startDay, @Param("endDay") LocalDateTime endDay);

	@Query("select s " +
		"from Store s " +
		"order by ((0.713 * (s.averageRating / 5)) + (0.287 * (s.totalVisitCount * 0.01))) " +
		"desc " +
		"limit 10")
	List<Store> findStoresByWeightedAverageRating();

}
