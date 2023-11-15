package com.pd.gilgeorigoreuda.login.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    private String token;

    @Column(nullable = false)
    private Long memberId;

    public RefreshToken(final String token, final Long memberId) {
        this.token = token;
        this.memberId = memberId;
    }

}
