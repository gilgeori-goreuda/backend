package com.pd.gilgeorigoreuda.review.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;

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
	private List<ReviewImage> images;

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateReviewRating(Integer reviewRating) {
		this.reviewRating = reviewRating;
	}

	public void checkAuthor(Long memberId) {
		if (Objects.equals(this.member.getId(), memberId)) {
			throw new RuntimeException("Mismatched Review");
		}
	}

}
