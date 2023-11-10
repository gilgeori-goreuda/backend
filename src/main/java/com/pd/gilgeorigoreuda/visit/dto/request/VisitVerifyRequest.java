package com.pd.gilgeorigoreuda.visit.dto.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class VisitVerifyRequest {

    @NotNull(message = "위도를 입력해주세요.")
    @Positive(message = "음수 값은 허용되지 않습니다.")
    @Digits(integer = 3, fraction = 38)
    private BigDecimal lat;

    @NotNull(message = "경도를 입력해주세요.")
    @Positive(message = "음수 값은 허용되지 않습니다.")
    @Digits(integer = 3, fraction = 38)
    private BigDecimal lng;

    public VisitVerifyRequest(final BigDecimal lat, final BigDecimal lng) {
        this.lat = lat;
        this.lng = lng;
    }

}
