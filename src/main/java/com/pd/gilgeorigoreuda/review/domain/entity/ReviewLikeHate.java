package com.pd.gilgeorigoreuda.review.domain.entity;

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
@Table(name = "review_likes_hates")
public class ReviewLikeHate extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "preference_type", nullable = false)
	private ReviewPreferenceType preferenceType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_review_likes_hates_member_id"))
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "fk_review_likes_hates_review_id"))
	private Review review;

}
