package com.pd.gilgeorigoreuda.community.controller;

import com.pd.gilgeorigoreuda.community.service.CommunityService;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community")
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping("/review")
    public Page<ReviewResponse> findAllReview(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "sort", required = false, defaultValue = "createdAt") String sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return communityService.findAll(pageable);
    }
}
