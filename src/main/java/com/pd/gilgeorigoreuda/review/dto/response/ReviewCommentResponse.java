package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import lombok.Getter;

@Getter
public class ReviewCommentResponse {
    private String content;

    public ReviewCommentResponse(ReviewComment reviewComment) {
        this.content = reviewComment.getContent();
    }
}
