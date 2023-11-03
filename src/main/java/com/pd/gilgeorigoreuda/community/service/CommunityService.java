package com.pd.gilgeorigoreuda.community.service;

import com.pd.gilgeorigoreuda.review.dto.response.ReviewResponse;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final ReviewRepository reviewRepository;

    public Page<ReviewResponse> findAll(Pageable pageable) {
        return reviewRepository.findAllReviewsWithImagesOrderByRecent(pageable);
    }
}
