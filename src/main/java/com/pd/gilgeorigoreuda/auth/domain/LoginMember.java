package com.pd.gilgeorigoreuda.auth.domain;

import lombok.Getter;

@Getter
public class LoginMember {

    private final Long memberId;
    private final Authority authority;

    public LoginMember(final Long memberId, final Authority authority) {
        this.memberId = memberId;
        this.authority = authority;
    }

    public static LoginMember guest() {
        return new LoginMember(0L, Authority.GUEST);
    }

    public static LoginMember member(final Long memberId) {
        return new LoginMember(memberId, Authority.MEMBER);
    }

    public boolean isMember() {
        return Authority.MEMBER.equals(authority);
    }

}
