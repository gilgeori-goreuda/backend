package com.pd.gilgeorigoreuda.auth;

import com.pd.gilgeorigoreuda.auth.domain.LoginMember;
import com.pd.gilgeorigoreuda.auth.exception.UnAuthorizedException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class CheckIsMember {

    @Before("@annotation(com.pd.gilgeorigoreuda.auth.MemberOnly)")
    public void check(final JoinPoint joinPoint) {
        if (joinPoint.getArgs().length == 0) {
            return;
        }

        Arrays.stream(joinPoint.getArgs())
                .filter(LoginMember.class::isInstance)
                .map(LoginMember.class::cast)
                .filter(LoginMember::isMember)
                .findFirst()
                .orElseThrow(UnAuthorizedException::new);
    }

}
