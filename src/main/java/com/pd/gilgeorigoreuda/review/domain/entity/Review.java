package com.pd.gilgeorigoreuda.review.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "reviews")
public class Review extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content", length = 300)
	private String content;

	@Column(name = "review_rating")
	@Builder.Default
	private Integer reviewRating = 0;

	@Column(name = "like_count")
	@Builder.Default
	private Integer likeCount = 0;

	@Column(name = "hate_count")
	@Builder.Default
	private Integer hateCount = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_review_member_id"))
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_review_store_id"))
	private Store store;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private List<ReviewImage> images = new ArrayList<>();

	public void updateReview(final String content, final Integer reviewRating) {
		this.content = content;
		this.reviewRating = reviewRating;
	}

	public void checkAuthor(final Long memberId) {
		if (Objects.equals(this.member.getId(), memberId)) {
			throw new RuntimeException("Mismatched Review");
		}
	}

	public void addOrUpdateImages(final List<ReviewImage> images) {
		if (!this.images.isEmpty()) {
			this.images.clear();
		}

		this.images.addAll(images);
	}

}
