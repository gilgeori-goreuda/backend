package com.pd.gilgeorigoreuda.community.service;

import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewListResponse;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommunityService {

    private final ReviewRepository reviewRepository;

    public ReviewListResponse findAll(final Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllReviewsWithImagesOrderByRecent(pageable);
        return ReviewListResponse.of(reviewPage);
    }

}
