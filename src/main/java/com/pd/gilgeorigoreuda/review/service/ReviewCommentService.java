package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCommentRequest;
import com.pd.gilgeorigoreuda.review.repository.ReviewCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCommentService {
    private final ReviewCommentRepository commentRepository;

    public void saveComment(Long reviewId, Long memberId , ReviewCommentRequest request) {
        ReviewComment comment = ReviewComment.builder()
                .content(request.getContent())
                .review(Review.builder().id(reviewId).build())
                .member(Member.builder().id(memberId).build())
                .build();
        commentRepository.save(comment);
    }
}
