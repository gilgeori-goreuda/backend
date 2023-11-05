package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.fileUpload.service.FileUploadService;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.repository.ReviewImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;
    private final FileUploadService fileUploadService;


    @Transactional
    public void createReview(final Long storeId,
                             final Long memberId,
                             final ReviewCreateRequest request,
                             final List<MultipartFile> files) {

        Review review = Review.builder()
                .content(request.getContent())
                .store(Store.builder().id(storeId).build())
                .member(Member.builder().id(memberId).build())
                .build();

        Review savedReview = reviewRepository.save(review);

        List<String> fileNames = fileUploadService.fileUpload(files);

        fileNames.forEach(name -> {
            reviewImageRepository.save(
                    ReviewImage
                        .builder()
                        .imageUrl(name)
                        .review(Review.builder().id(savedReview.getId()).build())
                        .build());
        });

    }

    public void updateReview(final Long reviewId, final Long memberId, final ReviewUpdateRequest reviewRequest) {
        Review review = getReview(reviewId);

        review.checkAuthor(memberId);

        review.updateContent(reviewRequest.getContent());
        review.updateReviewRating(review.getReviewRating());

        reviewRepository.save(review);
    }

    public void deleteReview(final Long reviewId, final Long memberId) {
        Review review = getReview(reviewId);

        review.checkAuthor(memberId);

        reviewRepository.deleteById(reviewId);
    }

    private Review getReview(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }
}
