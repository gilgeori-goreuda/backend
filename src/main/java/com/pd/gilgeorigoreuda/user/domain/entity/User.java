package com.pd.gilgeorigoreuda.user.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "users")
public class User extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 10)
	private String userName;

	@Column(nullable = false, length = 50)
	private String email;

	@Column(nullable = false, length = 10)
	private String nickname;

	@Column(nullable = false)
	@Builder.Default
	private Integer score = 0;

	@Column(name = "profile_image", columnDefinition = "TEXT")
	private String profileImage;

}
