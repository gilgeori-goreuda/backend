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

	@Query("SELECT s "
		+ "FROM Store s "
		+ "LEFT JOIN FETCH s.member m "
		+ "LEFT JOIN FETCH s.foodCategories fc "
		+ "WHERE s.id = :storeId")
	Optional<Store> findStoreWithMemberAndCategories(@Param("storeId") final Long storeId);

	@Query("SELECT s "
		+ "FROM Store s "
		+ "LEFT JOIN FETCH s.member m "
		+ "WHERE s.id = :storeId")
	Optional<Store> findStoreWithMember(@Param("storeId") final Long storeId);

	@Query("SELECT s " +
		"FROM Store s " +
		"WHERE s.createdAt >= :startDay AND s.createdAt <= :endDay " +
		"ORDER BY s.createdAt DESC " +
		"LIMIT 10")
	List<Store> findStoresByBetweenDay(@Param("startDay") final LocalDateTime startDay, @Param("endDay") final LocalDateTime endDay);

	@Query("SELECT s " +
		"FROM Store s " +
		"ORDER BY ((0.713 * (s.averageRating / 5)) + (0.287 * (s.totalVisitCount * 0.01))) DESC " +
		"LIMIT 10")
	List<Store> findStoresByRateAndVisitCount();

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
