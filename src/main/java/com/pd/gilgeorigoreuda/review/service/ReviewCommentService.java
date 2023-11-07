package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewComment;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCommentCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewCommentListResponse;
import com.pd.gilgeorigoreuda.review.repository.ReviewCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewCommentService {
    
    private final ReviewCommentRepository commentRepository;
    public void saveComment(final Long reviewId, final Long memberId , final ReviewCommentCreateRequest request) {
        ReviewComment comment = ReviewComment.builder()
                .content(request.getContent())
                .review(Review.builder().id(reviewId).build())
                .member(Member.builder().id(memberId).build())
                .build();

        commentRepository.save(comment);
    }
    @Transactional(readOnly = true)
    public ReviewCommentListResponse findCommentsByReviewId(final Long reviewId, final Pageable pageable) {
        Page<ReviewComment> reviewCommentPage = commentRepository.findAllByReviewId(reviewId, pageable);

        return ReviewCommentListResponse.of(reviewCommentPage);
    }

    public void updateComment(final Long commentId, final Long memberId, final ReviewCommentCreateRequest commentRequest) {
        ReviewComment reviewComment = getReviewComment(commentId);
        reviewComment.validateCommentAuthor(memberId);
        reviewComment.updateReviewComment(commentRequest.getContent());

        commentRepository.save(reviewComment);
    }

    public void deleteReviewComment(final Long commentId, final Long memberId) {
        ReviewComment reviewComment = getReviewComment(commentId);
        reviewComment.validateCommentAuthor(memberId);

        commentRepository.deleteById(commentId);
    }

    private ReviewComment getReviewComment(final Long commentId) {
        ReviewComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("ReviewComment not found"));

        return comment;
    }
}
