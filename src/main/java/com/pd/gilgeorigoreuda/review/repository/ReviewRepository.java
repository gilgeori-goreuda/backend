package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r " +
            "left join fetch r.store " +
            "left join fetch r.images " +
            "where r.member.id = :memberId")
    List<Review> findAllByMemberId(@Param("memberId") final Long memberId);

    @Query("select distinct r from Review r " +
            "left join fetch r.images i")
    Page<Review> findAllReviewsWithImagesOrderByRecent(final Pageable pageable);


    @Query("select r from Review r " +
            "left join fetch r.images " +
            "where r.store.id = :storeId ")
    Page<Review> findAllByStoreIdWithImages(@Param("storeId") Long storeId, Pageable pageable);
}
