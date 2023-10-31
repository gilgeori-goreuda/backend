package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
