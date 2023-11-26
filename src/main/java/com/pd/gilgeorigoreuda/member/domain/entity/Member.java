package com.pd.gilgeorigoreuda.member.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;

import jakarta.persistence.*;
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
		name = "members",
		indexes = {
				@Index(name = "idx_member_social_id", columnList = "social_id", unique = true),
				@Index(name = "idx_member_nickname", columnList = "nickname", unique = true)
		}
)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 30, unique = true)
	private String nickname;

	@Column(name = "profile_image_url", length = 512)
	private String profileImageUrl;

	@Column(name = "social_id", length = 30, nullable = false)
	private String socialId;

}
