package com.pd.gilgeorigoreuda.member.repository;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
