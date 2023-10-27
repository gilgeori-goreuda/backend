package com.pd.gilgeorigoreuda.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
    private String content;
    private Double reviewRating;
    private Integer likeCount;
    private Long userId;
    private List<String> imageUrls;
}
