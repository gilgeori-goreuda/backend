package com.pd.gilgeorigoreuda.review.dto.response;

import com.pd.gilgeorigoreuda.common.page.PageInfo;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewCommentListResponse {

    private List<ReviewCommentResponse> reviewComments;
    private PageInfo pageInfo;

    private ReviewCommentListResponse(List<ReviewCommentResponse> reviewComments,PageInfo pageInfo) {
        this.reviewComments = reviewComments;
        this.pageInfo = pageInfo;
    }

    public static ReviewCommentListResponse of(Page<ReviewComment> reviewCommentPage) {
        return new ReviewCommentListResponse(
                reviewCommentPage.getContent()
                        .stream()
                        .map(ReviewCommentResponse::new)
                        .toList(),
                PageInfo.of(reviewCommentPage)
        );
    }

}
