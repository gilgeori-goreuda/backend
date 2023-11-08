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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

	private final ReviewService reviewService;
	private final ReviewCommentService commentService;

	@PostMapping(value = "/stores/{storeId}/members/{memberId}",
				consumes = {MediaType.APPLICATION_JSON_VALUE,
							MediaType.MULTIPART_FORM_DATA_VALUE}
	)
	public ResponseEntity<Void> createReview(
			@PathVariable("storeId") Long storeId,
			@PathVariable("memberId") Long memberId,
			@RequestPart ReviewCreateRequest request,
			@RequestPart List<MultipartFile> files
	) {
		reviewService.createReview(storeId, memberId, request, files);

		return ResponseEntity
				.ok()
				.build();
	}

	@PutMapping("{reviewId}/members/{memberId}")
	public ResponseEntity<Void> updateReview(
		@PathVariable("reviewId") Long reviewId,
		@PathVariable("memberId") Long memberId,
		@RequestBody ReviewUpdateRequest reviewRequest
	) {
		reviewService.updateReview(reviewId, memberId, reviewRequest);

		return ResponseEntity
				.ok()
				.build();
	}

	@DeleteMapping("{reviewId}/members/{memberId}")
	public ResponseEntity<Void> deleteReview(
		@PathVariable("reviewId") Long reviewId,
		@PathVariable("memberId") Long memberId
	) {
		reviewService.deleteReview(reviewId, memberId);

		return ResponseEntity
				.ok()
				.build();
	}

	@PostMapping("{reviewId}/members/{memberId}/comments")
	public ResponseEntity<Void> saveComment(
		@PathVariable("reviewId") Long reviewId,
		@PathVariable("memberId") Long memberId,
		@RequestBody @Valid ReviewCommentCreateRequest commentRequest
	) {
		commentService.saveComment(reviewId, memberId, commentRequest);

		return ResponseEntity
				.ok()
				.build();
	}

	@GetMapping("{reviewId}/comments")
	public ResponseEntity<ReviewCommentListResponse> findAllComment(
		@PathVariable("reviewId") final Long reviewId,
		@RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
		@RequestParam(name = "size", required = false, defaultValue = "10") final Integer size
	) {
		ReviewCommentListResponse response = commentService.findCommentsByReviewId(reviewId,
			PageRequest.of(page, size));

		return ResponseEntity
			.ok()
			.body(response);
	}

	@PutMapping("/comments/{commentId}/members/{memberId}")
	public ResponseEntity<Void> updateReviewComment(@PathVariable("commentId") Long commentId,
		@PathVariable("memberId") Long memberId,
		@RequestBody ReviewCommentCreateRequest commentRequest
	) {
		commentService.updateComment(commentId, memberId, commentRequest);

		return ResponseEntity
				.ok()
				.build();
	}

	@DeleteMapping("/comments/{commentId}/members/{memberId}")
	public ResponseEntity<Void> deleteReviewComment(
		@PathVariable("commentId") Long commentId,
		@PathVariable("memberId") Long memberId
	) {
		commentService.deleteReviewComment(commentId, memberId);

		return ResponseEntity
				.ok()
				.build();
	}
}