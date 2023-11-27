package com.pd.gilgeorigoreuda.member.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(final String socialId);

    boolean existsByNickname(final String nicknameWithRandomNumber);

    @Query("select m from Member m left join fetch m.memberActiveInfo where m.id = :memberId")
    Member findMemberInfoAndActiveInfoById(final Long memberId);

}
