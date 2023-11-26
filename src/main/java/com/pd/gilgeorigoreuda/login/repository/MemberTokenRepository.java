package com.pd.gilgeorigoreuda.login.repository;

import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberTokenRepository extends JpaRepository<MemberToken, Long> {

    @Modifying
    @Query("delete from MemberToken mt where mt.accessToken = :accessToken")
    void deleteByAccessToken(@Param("accessToken") final String accessToken);

    @Modifying
    @Query("update MemberToken mt set mt.accessToken = :accessToken where mt.memberId = :memberId")
    void updateAccessToken(@Param("memberId") final Long memberId, @Param("accessToken") final String accessToken);

    Optional<MemberToken> findByAccessToken(final String accessToken);

}
