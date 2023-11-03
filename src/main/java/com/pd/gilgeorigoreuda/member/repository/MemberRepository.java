package com.pd.gilgeorigoreuda.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
