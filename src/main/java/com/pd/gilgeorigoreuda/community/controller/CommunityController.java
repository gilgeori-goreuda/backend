package com.pd.gilgeorigoreuda.community.controller;

import com.pd.gilgeorigoreuda.community.service.CommunityService;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class CommunityController {

    private final CommunityService communityService;

    @GetMapping("/reviews")
    public ResponseEntity<ReviewListResponse> findAllReview(
            @RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") final Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") final String sort
    ) {
        ReviewListResponse response = communityService.findAll(PageRequest.of(page, size, Sort.by(sort)));

        return ResponseEntity
            .ok()
            .body(response);
    }

}
