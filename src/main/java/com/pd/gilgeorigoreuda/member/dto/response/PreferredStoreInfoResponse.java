package com.pd.gilgeorigoreuda.member.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodType;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StoreType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PreferredStoreInfoResponse {

    private Long storeId;
    private String storeName;
    private Double averageRating;
    private String storeType;
    private String storeImageUrl;

    @JsonFormat(pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime createdAt;

    private List<String> categories;

    private PreferredStoreInfoResponse(
            final Long storeId,
            final String storeName,
            final Double averageRating,
            final String storeType,
            final String storeImageUrl,
            final LocalDateTime createdAt,
            final List<String> categories) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.averageRating = averageRating;
        this.storeType = storeType;
        this.storeImageUrl = storeImageUrl;
        this.createdAt = createdAt;
        this.categories = categories;
    }

    public static PreferredStoreInfoResponse of(final Store store) {
        return new PreferredStoreInfoResponse(
                store.getId(),
                store.getName(),
                store.getAverageRating(),
                store.getStoreType().getStoreTypeName(),
                store.getImageUrl(),
                store.getCreatedAt(),
                store.getFoodCategories()
                        .stream()
                        .map(FoodCategory::getFoodType)
                        .map(FoodType::getFoodName)
                        .toList()
        );
    }

}
