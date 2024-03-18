package com.pd.gilgeorigoreuda.auth;

import com.pd.gilgeorigoreuda.auth.annotation.MemberOnly;
import com.pd.gilgeorigoreuda.auth.aop.CheckIsMember;
import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.auth.exception.UnAuthorizedException;
import org.aspectj.lang.JoinPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@MockitoSettings
class CheckIsMemberTest {

    @InjectMocks
    private CheckIsMember checkIsMember;

    @Mock
    private JoinPoint joinPoint;

    @Test
    @DisplayName("MemberOnly 어노테이션이 붙은 메서드에 로그인한 회원이면 예외를 던지지 않는다.")
    @MemberOnly
    void checkWhenMemberIsLogInShouldNotThrowException() {
        // given
        LoginMember loginMember = LoginMember.member(1L);
        when(joinPoint.getArgs()).thenReturn(new Object[]{loginMember});

        // when, then
        assertThatCode(() -> checkIsMember.check(joinPoint))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("MemberOnly 어노테이션이 붙은 메서드에 로그인하지 않은 회원이면 예외를 던진다.")
    @MemberOnly
    void checkWhenMemberIsNotLogInShouldThrowUnAuthorizedException() {
        // given
        LoginMember loginMember = LoginMember.guest();
        when(joinPoint.getArgs()).thenReturn(new Object[]{loginMember});

        // when, then
        assertThatThrownBy(() -> checkIsMember.check(joinPoint))
                .isInstanceOf(UnAuthorizedException.class);
    }

    @Test
    @DisplayName("MemberOnly 어노테이션이 붙은 메서드에 인자가 없으면 예외를 던지지 않는다.")
    void checkWhenNotAnnotatedWithMemberOnlyShouldNotThrowException() {
        // given
        when(joinPoint.getArgs()).thenReturn(new Object[]{});

        // when, then
        assertThatCode(() -> checkIsMember.check(joinPoint))
                .doesNotThrowAnyException();
    }

}