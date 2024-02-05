package com.pd.gilgeorigoreuda.visit.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class VisitResponse {

    private Long visitRecordId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime visitRecordTime;

    private Boolean isVisited;
    private Integer walkingDistance;
    private Integer approximateWalkingTime;

    public VisitResponse(
            final Long visitRecordId,
            final LocalDateTime visitRecordTime,
            final Boolean isVisited,
            final Integer walkingDistance,
            final Integer approximateWalkingTime) {
        this.visitRecordId = visitRecordId;
        this.visitRecordTime = visitRecordTime;
        this.isVisited = isVisited;
        this.walkingDistance = walkingDistance;
        this.approximateWalkingTime = approximateWalkingTime;
    }

    public static VisitResponse of(final StoreVisitRecord storeVisitRecord, final Integer approximateWalkingTime) {
        return new VisitResponse(
                storeVisitRecord.getId(),
                storeVisitRecord.getCreatedAt(),
                storeVisitRecord.getIsVisited(),
                storeVisitRecord.getWalkingDistance(),
                approximateWalkingTime
        );
    }

}
