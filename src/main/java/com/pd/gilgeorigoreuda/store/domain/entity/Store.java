package com.pd.gilgeorigoreuda.store.domain.entity;

import java.time.LocalDate;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.user.domain.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "stores")
public class Store extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "store_name", nullable = false, length = 50)
	private String storeName;

	@Column(name = "store_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private StoreType storeType;

	@Column(name = "store_number", nullable = false, length = 20)
	private String storeNumber;

	@Column(nullable = false, length = 200)
	private String introduction;

	@Column(name = "detail_location", nullable = false, length = 100)
	private String detailLocation;

	@Column(name = "store_average_rating", nullable = false)
	@Builder.Default
	private Double storeAverageRating = 0.0;

	@Column(name = "business_date", nullable = false, length = 20)
	private String businessDate;

	@Column(name = "open_time", length = 5)
	private String openTime;

	@Column(name = "close_time", length = 5)
	private String closeTime;

	@Column(name = "purchase_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseType purchaseType;

	@Column(name = "store_image", columnDefinition = "TEXT")
	private String storeImage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_store_user"))
	private User user;

}
