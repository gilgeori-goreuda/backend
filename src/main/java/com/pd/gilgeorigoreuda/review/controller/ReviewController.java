package com.pd.gilgeorigoreuda.review.controller;

import com.pd.gilgeorigoreuda.review.dto.request.ReviewRequest;
import com.pd.gilgeorigoreuda.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("{storeId}")
    public void save(@PathVariable("storeId") Long storeId,
                     @RequestBody ReviewRequest request) {
        reviewService.createReview(storeId, request);
    }

}

