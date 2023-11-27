package com.pd.gilgeorigoreuda.member.domain.entity;

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
@Table(name = "member_active_info")
public class MemberActiveInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private MemberLevel memberLevel = MemberLevel.BEGINNER;

	@Column(name = "total_walking_distance", nullable = false)
	@Builder.Default
	private Integer totalWalkingDistance = 0;

	@Column(name = "total_visit_count", nullable = false)
	@Builder.Default
	private Integer totalVisitCount = 0;

	@Column(name = "exp", nullable = false)
	@Builder.Default
	private Integer exp = 0;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_member_active_info_member_id"))
	private Member member;

}
