package com.pd.gilgeorigoreuda.auth;

import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.auth.exception.UnAuthorizedException;
import org.aspectj.lang.JoinPoint;
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
    void checkWhenNotAnnotatedWithMemberOnlyShouldNotThrowException() {
        // given
        when(joinPoint.getArgs()).thenReturn(new Object[]{});

        // when, then
        assertThatCode(() -> checkIsMember.check(joinPoint))
                .doesNotThrowAnyException();
    }

}