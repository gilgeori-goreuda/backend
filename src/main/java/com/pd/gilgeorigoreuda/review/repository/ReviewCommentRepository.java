package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {

    @Query("select rc " +
            "from ReviewComment rc " +
            "left join fetch rc.member " +
            "where rc.review.id = :reviewId")
    Page<ReviewComment> findAllByReviewId(@Param("reviewId") Long reviewId, Pageable pageable);

}
