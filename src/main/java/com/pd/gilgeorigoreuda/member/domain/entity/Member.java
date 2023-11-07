package com.pd.gilgeorigoreuda.member.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
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
	name = "members",
	indexes = {
			@Index(name = "idx_members_email", columnList = "email"),
			@Index(name = "idx_members_nickname", columnList = "nickname")
	}
)
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 200, unique = true)
	private String email;

	@Column(nullable = false, length = 100, unique = true)
	private String nickname;

	@Column(name = "profile_image_url", length = 512)
	private String profileImageUrl;

}
