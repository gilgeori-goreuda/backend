package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	Optional<Store> findStoreWithMemberAndCategories(@Param("storeId") final Long storeId);

	@Query("select s "
		+ "from Store s "
		+ "left join fetch s.member m "
		+ "where s.id = :storeId")
	Optional<Store> findStoreWithMember(@Param("storeId") final Long storeId);

	@Query("select s " +
		"from Store s " +
		"where s.createdAt >= :startDay and s.createdAt <= :endDay " +
		"order by s.createdAt desc " +
		"limit 10")
	List<Store> findAllByBetweenDay(@Param("startDay") final LocalDateTime startDay, @Param("endDay") final LocalDateTime endDay);

	@Query("select s " +
		"from Store s " +
		"order by ((0.713 * (s.averageRating / 5)) + (0.287 * (s.totalVisitCount * 0.01))) " +
		"desc " +
		"limit 10")
	List<Store> findStoresByWeightedAverageRating();

	@Modifying(clearAutomatically = true)
	@Query(value = "update stores s " +
			"set average_rating = (" +
			"SELECT ROUND(AVG(review_rating), 1)" +
			"from reviews where store_id = s.id " +
			"group by store_id) " +
			"where id " +
			"in(select store_id from reviews where store_id = s.id group by store_id)",
			nativeQuery = true)
	void updateAllStoresAvgRating();

	@Modifying(clearAutomatically = true)
	@Query(value = "update stores s " +
			"set total_visit_count = (" +
			"SELECT sum(is_visited)" +
			"from store_visit_records where store_id = s.id " +
			"group by store_id) " +
			"where id " +
			"in(select store_id from store_visit_records where store_id = s.id group by store_id)",
			nativeQuery = true)
	void updateAllStoresVisitCount();

	@Modifying(clearAutomatically = true)
	@Query(value = "update stores s " +
			"set total_report_count = (" +
			"SELECT count(*)" +
			"from store_report_histories where store_id = s.id " +
			"group by store_id) " +
			"where id " +
			"in(select store_id from store_report_histories where store_id = s.id group by store_id)",
			nativeQuery = true)
	void updateAllStoresReportCount();

	@Modifying(clearAutomatically = true)
	@Query(value = "update stores s " +
			"set is_blocked = true " +
			"where total_report_count >:count",
			nativeQuery = true)
	void updateBlockedStore(@Param("count") int count);

}
