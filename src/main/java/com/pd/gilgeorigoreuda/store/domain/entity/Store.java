package com.pd.gilgeorigoreuda.store.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;

import jakarta.persistence.Column;
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
		@Index(name = "idx_store_lat_lng", columnList = "lat, lng")
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

	// todo: open, close time 자료형 변경
	@Column(name = "open_time", length = 5)
	private String openTime;

	@Column(name = "close_time", length = 5)
	private String closeTime;

	@Column(name = "purchase_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseType purchaseType;

	@Column(name = "image_url", length = 512)
	private String imageUrl;

	@Column(nullable = false)
	private Double lat;

	@Column(nullable = false)
	private Double lng;

	@Column(name = "street_address", nullable = false, length = 128)
	private String streetAddress;

	@Column(name = "total_visit_count")
	@Builder.Default
	private Integer totalVisitCount = 0;

	@Column(name = "last_modified_user_id")
	private Long lastModifiedUserId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_store_member_id"))
	private Member member;

}
