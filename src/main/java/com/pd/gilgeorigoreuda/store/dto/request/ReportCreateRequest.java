package com.pd.gilgeorigoreuda.store.dto.request;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreReportHistory;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ReportCreateRequest {

    @NotBlank(message = "신고 내용을 입력해주세요.")
    @Size(min = 1, max = 300, message = "신고 내용은 1에서 300자 사이여야 합니다.")
    private String content;

    @NotNull(message = "위도를 입력해주세요.")
    @Positive(message = "음수 값은 허용되지 않습니다.")
    @Digits(integer = 3, fraction = 38)
    private BigDecimal memberLat;

    @NotNull(message = "경도를 입력해주세요.")
    @Positive(message = "음수 값은 허용되지 않습니다.")
    @Digits(integer = 3, fraction = 38)
    private BigDecimal memberLng;

    public ReportCreateRequest(final String content, final BigDecimal memberLat, final BigDecimal memberLng){
        this.content = content;
        this.memberLat = memberLat;
        this.memberLng = memberLng;
    }

    public StoreReportHistory toEntity(final Long storeId, final Long memberId){
        return StoreReportHistory.builder()
                .member(Member.builder().id(memberId).build())
                .store(Store.builder().id(storeId).build())
                .content(content)
                .build();
    }

}
