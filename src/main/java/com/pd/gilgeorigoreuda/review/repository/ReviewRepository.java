package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select distinct new com.pd.gilgeorigoreuda.review.dto.response.ReviewResponse(r) from Review r " +
//            "left join r.user " +
//            "left join r.store " +
            "join r.images " +
//            "on ri.review.id = r.id " +
            "where r.store.id = :storeId " +
            "and r.member.id= :memberId")
    List<ReviewResponse> findByReview(@Param("storeId") Long storeId, Long memberId);
}
