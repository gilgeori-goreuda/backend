package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.ReviewPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReviewPreferenceRepository extends JpaRepository<ReviewPreference, Long> {

    @Query(value = "select * from review_preferences where review_id = :reviewId and member_id = :memberId", nativeQuery = true)
    Optional<ReviewPreference> findByReviewIdAndMemberId(@Param("reviewId") final Long reviewId, @Param("memberId") final Long memberId);
}
