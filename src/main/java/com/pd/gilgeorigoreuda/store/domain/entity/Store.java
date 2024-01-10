package com.pd.gilgeorigoreuda.store.domain.entity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.dto.request.BusinessDateRequest;

import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(
	name = "stores",
	indexes = {
		@Index(name = "idx_store_large_medium_address_lat_lng", columnList = "large_address, medium_address, lat, lng"),
	}
)
public class Store extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "store_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private StoreType storeType;

	@Column(name = "detail_location", length = 300)
	private String detailLocation;

	@Column(name = "average_rating", nullable = false)
	@Builder.Default
	private Double averageRating = 0.0;

	@Column(name = "business_date", nullable = false, length = 100)
	private String businessDate;

	@Column(name = "open_time", length = 10)
	private LocalTime openTime;

	@Column(name = "close_time", length = 10)
	private LocalTime closeTime;

	@Column(name = "purchase_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseType purchaseType;

	@Column(name = "image_url", length = 512)
	private String imageUrl;

	@Column(name = "lat", precision = 22, scale = 16, nullable = false)
	private BigDecimal lat;

	@Column(name = "lng", precision = 22, scale = 16, nullable = false)
	private BigDecimal lng;

	@Embedded
	private StreetAddress streetAddress;

	@Column(name = "total_visit_count")
	@Builder.Default
	private Integer totalVisitCount = 0;

	@Column(name = "last_modified_member_nickname", length = 10)
	private String lastModifiedMemberNickname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_stores_member_id"))
	private Member member;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<FoodCategory> foodCategories = new ArrayList<>();

	@Column(name = "total_report_count", nullable = false)
	@Builder.Default
	private Integer totalReportCount = 0;

	@Column(name = "is_blocked", nullable = false)
	@Builder.Default
	private Boolean isBlocked = false;

	public void addFoodCategories(final List<FoodCategory> foodCategories) {
		if (!this.foodCategories.isEmpty()) {
			this.foodCategories.clear();
		}

		for (FoodCategory foodCategory : foodCategories) {
			this.foodCategories.add(foodCategory);
			foodCategory.setStore(this);
		}
	}

	public void updateStoreInfo(final StoreUpdateRequest storeUpdateRequest, final String lastModifiedMemberNickname) {
		this.name = storeUpdateRequest.getName();
		this.storeType = StoreType.of(storeUpdateRequest.getStoreType());
		this.openTime = storeUpdateRequest.getOpenTime();
		this.closeTime = storeUpdateRequest.getCloseTime();
		this.purchaseType = PurchaseType.of(storeUpdateRequest.getPurchaseType());
		this.businessDate = BusinessDateRequest.of(storeUpdateRequest.getBusinessDates()).toString();
		this.imageUrl = storeUpdateRequest.getImageUrl();
		this.lat = storeUpdateRequest.getLat();
		this.lng = storeUpdateRequest.getLng();
		this.streetAddress = StreetAddress.of(storeUpdateRequest.getStreetAddress());
		this.lastModifiedMemberNickname = lastModifiedMemberNickname;
	}

	public boolean isOwner(final Long memberId) {
		return this.member.getId().equals(memberId);
	}

	public boolean isSameImage(final String newImageUrl) {
		return this.imageUrl.equals(newImageUrl);
	}

}
