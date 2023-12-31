package com.pd.gilgeorigoreuda.store.domain.entity;

import com.pd.gilgeorigoreuda.common.entity.BaseTimeEntity;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.visit.exception.NotVerifiedVisitRecordException;
import com.pd.gilgeorigoreuda.visit.exception.TimeOutException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Table(name = "store_visit_records")
@DynamicUpdate
public class StoreVisitRecord extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "walking_distance", nullable = false)
	private Integer walkingDistance;

	@Column(name = "is_visited", nullable = false)
	@Builder.Default
	private Boolean isVisited = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", foreignKey = @ForeignKey(name = "fk_store_visit_records_store_id"))
	private Store store;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "fk_store_visit_records_member_id"))
	private Member member;

	public static StoreVisitRecord from (final Long memberId, final Long storeId, final Integer walkingDistance) {
		return StoreVisitRecord.builder()
				.walkingDistance(walkingDistance)
				.member(Member.builder().id(memberId).build())
				.store(Store.builder().id(storeId).build())
				.build();
	}

	public void checkTimeOut(int validTime) {
		LocalDateTime startVisitTime = this.getCreatedAt();
		LocalDateTime endVisitTime = LocalDateTime.now();

		if (startVisitTime.plusHours(validTime).isBefore(endVisitTime)) {
			throw new TimeOutException();
		}
	}

	public void verifyVisit() {
		this.isVisited = true;
	}

	public void isVerifiedVisitRecord() {
		if (!this.isVisited) {
			throw new NotVerifiedVisitRecordException();
		}
	}

}
