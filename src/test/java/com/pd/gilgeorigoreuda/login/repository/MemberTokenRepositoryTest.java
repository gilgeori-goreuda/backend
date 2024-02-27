package com.pd.gilgeorigoreuda.login.repository;

import com.pd.gilgeorigoreuda.login.domain.MemberToken;
import com.pd.gilgeorigoreuda.settings.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberTokenFixtures.*;
import static org.assertj.core.api.Assertions.*;

class MemberTokenRepositoryTest extends RepositoryTest {

    @Test
    @DisplayName("AccessToken을 통해 MemberToken 객체 조회")
    void findByAccessToken() {
        // given
        MemberToken KIM_TOKEN = dataBuilder.buildMemberToken(KIM_TOKEN());
        String accessToken = KIM_TOKEN.getAccessToken();

        // when & then
        memberTokenRepository.findByAccessToken(accessToken)
                .ifPresent(
                        memberToken -> assertThat(accessToken).isEqualTo(memberToken.getAccessToken())
                );

    }

    @Test
    @DisplayName("AccessToken을 통해 MemberToken 객체 삭제")
    void deleteByAccessToken() {
        // given
        MemberToken KIM_TOKEN = dataBuilder.buildMemberToken(KIM_TOKEN());
        String accessToken = KIM_TOKEN.getAccessToken();

        // when
        memberTokenRepository.deleteByAccessToken(accessToken);

        // then
        assertThat(memberTokenRepository.findByAccessToken(accessToken)).isEmpty();
    }

    @Test
    @DisplayName("MemberId를 통해 AccessToken을 업데이트")
    void updateAccessToken() {
        // given
        MemberToken KIM_TOKEN = dataBuilder.buildMemberToken(KIM_TOKEN());
        MemberToken LEE_TOKEN = dataBuilder.buildMemberToken(LEE_TOKEN());
        Long memberId = KIM_TOKEN.getMemberId();
        String newAccessToken = "Kims New AccessToken";

        // when
        memberTokenRepository.updateAccessToken(memberId, newAccessToken);

        // then
        memberTokenRepository.findByAccessToken(newAccessToken)
                .ifPresent(
                        newMemberToken -> assertThat(newMemberToken.getAccessToken()).isEqualTo(newAccessToken)
                );
    }

}