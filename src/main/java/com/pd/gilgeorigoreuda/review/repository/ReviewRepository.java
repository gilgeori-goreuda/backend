package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r " +
            "left join fetch r.store " +
            "left join fetch r.images " +
            "where r.member.id = :memberId")
    List<Review> findAllByMemberId(@Param("memberId") final Long memberId);

    @Query("select r from Review r " +
            "left join fetch r.images i")
    Page<Review> findAllReviewsWithImagesOrderByCondition(final Pageable pageable);


    @Query("select r from Review r " +
            "left join fetch r.images " +
            "where r.store.id = :storeId ")
    Page<Review> findAllByStoreIdWithImages(@Param("storeId") Long storeId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("select r from Review r " +
            "left join fetch r.images " +
            "where r.id = :reviewId")
    Optional<Review> findReviewWithReviewImages(final Long reviewId);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE reviews " +
                    "SET like_count = COALESCE(( " +
                    "    SELECT COUNT(*) AS count " +
                    "    FROM review_preferences " +
                    "    WHERE preference_type = 'LIKE' " +
                    "    AND review_id = reviews.id " +
                    "    GROUP BY review_id " +
                    "), 0) " +
                    "WHERE id IN ( " +
                    "    SELECT review_id " +
                    "    FROM review_preferences " +
                    "    WHERE preference_type = 'LIKE' " +
                    "    GROUP BY review_id)"
    )
    void updateAllReviewLikeCount();

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "UPDATE reviews " +
                    "SET hate_count = COALESCE(( " +
                    "    SELECT COUNT(*) AS count " +
                    "    FROM review_preferences " +
                    "    WHERE preference_type = 'HATE' " +
                    "    AND review_id = reviews.id " +
                    "    GROUP BY review_id " +
                    "), 0) " +
                    "WHERE id IN ( " +
                    "    SELECT review_id " +
                    "    FROM review_preferences " +
                    "    WHERE preference_type = 'HATE' " +
                    "    GROUP BY review_id)"
    )
    void updateAllReviewHateCount();

}
