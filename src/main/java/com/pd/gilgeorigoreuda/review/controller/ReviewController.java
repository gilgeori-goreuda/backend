package com.pd.gilgeorigoreuda.review.controller;

import com.pd.gilgeorigoreuda.review.dto.request.ReviewCommentCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewCommentListResponse;
import com.pd.gilgeorigoreuda.review.service.ReviewCommentService;
import com.pd.gilgeorigoreuda.review.service.ReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewCommentService commentService;

	@PostMapping("/store/{storeId}/member/{memberId}")
	public void createReview(
		@PathVariable("storeId") Long storeId,
		@PathVariable("memberId") Long memberId,
		@RequestBody @Valid ReviewCreateRequest request
	) {
		reviewService.createReview(storeId, memberId, request);
	}

	@PutMapping("{reviewId}/member/{memberId}")
	public void updateReview(
		@PathVariable("reviewId") Long reviewId,
		@PathVariable("memberId") Long memberId,
		@RequestBody ReviewUpdateRequest reviewRequest
	) {
		reviewService.updateReview(reviewId, memberId, reviewRequest);
	}

	@DeleteMapping("{reviewId}/member/{memberId}")
	public void deleteReview(
		@PathVariable("reviewId") Long reviewId,
		@PathVariable("memberId") Long memberId
	) {
		reviewService.deleteReview(reviewId, memberId);
	}

	@PostMapping("{reviewId}/comment/member/{memberId}")
	public void saveComment(@PathVariable("reviewId") Long reviewId,
		@PathVariable("memberId") Long memberId,
		@RequestBody @Valid ReviewCommentCreateRequest commentRequest
	) {
		commentService.saveComment(reviewId, memberId, commentRequest);
	}

	@GetMapping("{reviewId}/comment")
	public ResponseEntity<ReviewCommentListResponse> findAllComment(
		@PathVariable("reviewId") Long reviewId,
		@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
		@RequestParam(name = "size", required = false, defaultValue = "10") Integer size
	) {
		ReviewCommentListResponse response = commentService.findCommentsByReviewId(reviewId,
			PageRequest.of(page, size));

		return ResponseEntity
			.ok()
			.body(response);
	}

	@PutMapping("/comment/{commentId}/member/{memberId}")
	public void updateReviewComment(@PathVariable("commentId") Long commentId,
		@PathVariable("memberId") Long memberId,
		@RequestBody ReviewCommentCreateRequest commentRequest
	) {
		commentService.updateComment(commentId, memberId, commentRequest);
	}

	@DeleteMapping("/comment/{commentId}/member/{memberId}")
	public void deleteReviewComment(
		@PathVariable("commentId") Long commentId,
		@PathVariable("memberId") Long memberId
	) {
		commentService.deleteReviewComment(commentId, memberId);
	}
}