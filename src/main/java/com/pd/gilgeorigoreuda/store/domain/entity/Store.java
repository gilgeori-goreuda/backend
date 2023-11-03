package com.pd.gilgeorigoreuda.store.domain.entity;

import java.time.LocalTime;
import java.util.List;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.store.dto.request.BusinessDateRequest;

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

import java.util.List;

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

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	@Column(name = "store_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private StoreType storeType;

	@Column(name = "detail_location", length = 100)
	private String detailLocation;

	@Column(name = "average_rating", nullable = false)
	@Builder.Default
	private Double averageRating = 0.0;

	@Column(name = "business_date", nullable = false, length = 20)
	private String businessDate;

	@Column(name = "open_time", length = 5)
	private LocalTime openTime;

	@Column(name = "close_time", length = 5)
	private LocalTime closeTime;

	@Column(name = "purchase_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseType purchaseType;

	@Column(name = "image_url", length = 512)
	private String imageUrl;

	@Column(nullable = false)
	private Double lat;

	@Column(nullable = false)
	private Double lng;

	@Embedded
	private StreetAddress streetAddress;

	@Column(name = "total_visit_count")
	@Builder.Default
	private Integer totalVisitCount = 0;

	@Column(name = "last_modified_member_nickname", length = 10)
	private String lastModifiedMemberNickname;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_store_member_id"))
	private Member member;

	@OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<FoodCategory> foodCategories;

	public void addFoodCategories(final List<FoodCategory> foodCategories) {
		if (!this.foodCategories.isEmpty()) {
			this.foodCategories.clear();
		}

		this.foodCategories.addAll(foodCategories);
	}

	public void updateBasicInfo(
			final String name,
			final String storeType,
			final LocalTime openTime,
			final LocalTime closeTime,
			final String purchaseType,
			final String businessDates,
			final Double lat,
			final Double lng,
			final String streetAddress,
			final String lastModifiedMemberNickname) {
		this.name = name;
		this.storeType = StoreType.of(storeType);
		this.openTime = openTime;
		this.closeTime = closeTime;
		this.purchaseType = PurchaseType.of(purchaseType);
		this.businessDate = BusinessDateRequest.of(businessDates).toString();
		this.lat = lat;
		this.lng = lng;
		this.streetAddress = StreetAddress.of(streetAddress);
		this.lastModifiedMemberNickname = lastModifiedMemberNickname;
	}

	public boolean isOwner(Long memberId) {
		return this.member.getId().equals(memberId);
	}

}
