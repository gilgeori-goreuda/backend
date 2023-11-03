package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r " +
            "left join fetch r.store " +
            "left join fetch r.images " +
            "where r.member.id = :memberId")
    List<Review> findAllByMemberId(@Param("memberId") Long memberId);

}
