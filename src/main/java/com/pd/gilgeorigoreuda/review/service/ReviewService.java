package com.pd.gilgeorigoreuda.review.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.review.domain.entity.ReviewImage;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewCreateRequest;
import com.pd.gilgeorigoreuda.review.dto.request.ReviewUpdateRequest;
import com.pd.gilgeorigoreuda.review.repository.ReviewImageRepository;
import com.pd.gilgeorigoreuda.review.repository.ReviewRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    UUID uuid = UUID.randomUUID();

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository reviewImageRepository;

    private final AmazonS3 amazonS3;
    @Value("aaaaabbbbucket")
    private String bucket;

    @Transactional
    public void createReview(final Long storeId, final Long memberId, final ReviewCreateRequest request, final List<MultipartFile> files) {
        Review review = Review.builder()
                .content(request.getContent())
                .store(Store.builder().id(storeId).build())
                .member(Member.builder().id(memberId).build())
                .build();

        Review savedReview = reviewRepository.save(review);

        for (MultipartFile file : files){
            String fileName = "reviewImage" + uuid.toString();
            reviewImageRepository.save(ReviewImage.builder()
                    .imageUrl(fileName)
                    .review(Review.builder().id(savedReview.getId()).build())
                    .build());
            //amazonS3.putObject(new PutObjectRequest(bucket, fileName, String.valueOf(file)));
        }




//        List<ReviewImage> reviewImages = request.getImageUrls()
//                .stream()
//                .map(
//                        image -> ReviewImage.builder()
//                                .imageUrl(image)
//                                .review(Review.builder().id(savedReview.getId()).build())
//                                .build()
//                )
//                .toList();
//
//        reviewImageRepository.saveAll(reviewImages);
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
