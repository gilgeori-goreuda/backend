package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewCommentResponse {
    private String content;
    private String nickname;
    private String profileImageUrl;
    private LocalDateTime createdAt;


    public ReviewCommentResponse(ReviewComment reviewComment) {
        this.content = reviewComment.getContent();
        this.nickname = reviewComment.getMember().getNickname();
        this.profileImageUrl = reviewComment.getMember().getProfileImageUrl();
        this.createdAt = reviewComment.getCreatedAt();
    }
}
