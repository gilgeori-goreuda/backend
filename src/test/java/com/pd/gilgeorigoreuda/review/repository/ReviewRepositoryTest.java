package com.pd.gilgeorigoreuda.review.repository;

import com.pd.gilgeorigoreuda.review.dto.response.ReviewResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewRepositoryTest {
@Autowired
ReviewRepository reviewRepository;
    @Test
    void createReview() {
        List<ReviewResponse> review = reviewRepository.findByReview(1L, 1L);
        assertEquals(review.size(),1);
        ReviewResponse reviewResponse = review.get(0);
        assertEquals(reviewResponse.getReviewId(),1l);
        assertEquals(reviewResponse.getImageUrls().size(),3);

    }
}