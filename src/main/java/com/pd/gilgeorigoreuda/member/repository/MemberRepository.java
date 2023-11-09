package com.pd.gilgeorigoreuda.member.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.HashMap;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT * FROM MEMBERS where provider_id = :providerId", nativeQuery = true)
    Optional<Member> findByProviderId(@Param("providerId") String providerId);
}
