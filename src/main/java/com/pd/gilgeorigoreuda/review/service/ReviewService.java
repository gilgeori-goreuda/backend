package com.pd.gilgeorigoreuda.review.service;

import com.pd.gilgeorigoreuda.image.service.ImageService;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewCreateResponse;
import com.pd.gilgeorigoreuda.review.dto.response.ReviewListResponse;
import com.pd.gilgeorigoreuda.review.exception.NoSuchReviewException;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;

import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;
import com.pd.gilgeorigoreuda.visit.exception.NoSuchStoreVisitRecordException;
import com.pd.gilgeorigoreuda.visit.repository.StoreVisitRecordRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageService imageService;
    private final StoreVisitRecordRepository storeVisitRecordRepository;

    @Transactional
    public ReviewCreateResponse createReview(final Long storeId, final Long memberId, final ReviewCreateRequest reviewCreateRequest) {
        StoreVisitRecord storeVisitRecord = getStoreVisitRecord(storeId, memberId);
        storeVisitRecord.isVerifiedVisitRecord();

        Review review = reviewCreateRequest.toEntity(memberId, storeId);

        List<ReviewImage> reviewImages = makeImages(reviewCreateRequest);
        review.addOrUpdateImages(reviewImages);

        Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.of(savedReview.getId());
    }

    @Transactional
    public void updateReview(final Long reviewId, final Long memberId, final ReviewUpdateRequest reviewUpdateRequest) {
        Review review = getReviewWithReviewImages(reviewId);

        review.checkAuthority(memberId);

        List<ReviewImage> reviewImages = makeUpdatedImages(reviewUpdateRequest, review);

        review.addOrUpdateImages(reviewImages);
        review.updateReview(reviewUpdateRequest.getContent(), reviewUpdateRequest.getReviewRating());

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(final Long reviewId, final Long memberId) {
        Review review = getReview(reviewId);

        review.checkAuthority(memberId);

        reviewRepository.deleteById(reviewId);
    }

    public ReviewListResponse findReviewsByStoreId(final Long storeId, final Pageable pageable) {
        Pageable sortedByDateDesc = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        Page<Review> reviewPage = reviewRepository.findAllByStoreIdWithImages(storeId, sortedByDateDesc);
        return ReviewListResponse.of(reviewPage);
    }

    private List<ReviewImage> makeUpdatedImages(final ReviewUpdateRequest reviewUpdateRequest, final Review review) {
        List<ReviewImage> originalImages = review.getImages();
        List<ReviewImage> updatedImages = reviewUpdateRequest.getImageUrls()
                .stream()
                .map(newImage -> makeUpdatedImage(newImage, originalImages))
                .toList();

        deleteNotUsedOriginalImages(originalImages, updatedImages);

        return updatedImages;
    }

    private ReviewImage makeUpdatedImage(final String newImage, final List<ReviewImage> originalImages) {
        return originalImages
                .stream()
                .filter(originalImage -> originalImage.getImageUrl().equals(newImage))
                .findAny()
                .orElseGet(() -> new ReviewImage(newImage));
    }

    private void deleteNotUsedOriginalImages(final List<ReviewImage> originalImages, final List<ReviewImage> updatedImages) {
        List<String> imagesToDelete = originalImages
                .stream()
                .filter(originalImage -> !updatedImages.contains(originalImage))
                .map(ReviewImage::getImageUrl)
                .toList();

        if (imagesToDelete.isEmpty()) {
            return;
        }

        imageService.deleteMultiImages(imagesToDelete);
    }

    private static List<ReviewImage> makeImages(final ReviewCreateRequest reviewCreateRequest) {
        return reviewCreateRequest.getImageUrls()
                .stream()
                .map(ReviewImage::new)
                .toList();
    }

    private Review getReview(final Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(NoSuchReviewException::new);
    }

    private Review getReviewWithReviewImages(final Long reviewId) {
        return reviewRepository.findReviewWithReviewImages(reviewId)
                .orElseThrow(NoSuchReviewException::new);
    }

    private StoreVisitRecord getStoreVisitRecord(Long storeId, Long memberId) {
        return storeVisitRecordRepository.findByMemberIdAndStoreId(memberId, storeId)
                .orElseThrow(NoSuchStoreVisitRecordException::new);
    }

}
