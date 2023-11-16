package com.pd.gilgeorigoreuda.login.repository;

import com.pd.gilgeorigoreuda.login.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(final String token);

    boolean existsByToken(final String token);

    @Modifying
    @Query("delete from RefreshToken refreshToken where refreshToken.memberId = :memberId")
    void deleteByMemberId(@Param("memberId") final Long memberId);

}
