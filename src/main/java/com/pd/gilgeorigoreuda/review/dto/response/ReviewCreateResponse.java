package com.pd.gilgeorigoreuda.review.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateResponse {

    private Long id;

    private ReviewCreateResponse(final Long id) {
        this.id = id;
    }

    public static ReviewCreateResponse of(final Long id) {
        return new ReviewCreateResponse(id);
    }

}
