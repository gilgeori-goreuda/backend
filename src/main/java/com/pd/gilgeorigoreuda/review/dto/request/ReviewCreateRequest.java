package com.pd.gilgeorigoreuda.review.dto.request;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.review.domain.entity.Review;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewCreateRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 1, message = "평점은 1~5 사이여야 합니다.")
    @Max(value = 5, message = "평점은 1~5 사이여야 합니다.")
    private Integer reviewRating;

    @Size(max = 5, message = "이미지는 최대 5개까지 첨부할 수 있습니다.")
    @URL(message = "유효한 URL을 입력해주세요.", regexp = "^(http|https)://(www\\.)?.*")
    private List<String> imageUrls;

    public Review toEntity(final Long memberId, final Long storeId) {
        return Review.builder()
                .content(content)
                .reviewRating(reviewRating)
                .member(Member.builder().id(memberId).build())
                .store(Store.builder().id(storeId).build())
                .build();
    }

}
