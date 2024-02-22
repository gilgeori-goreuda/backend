package com.pd.gilgeorigoreuda.settings;

import com.pd.gilgeorigoreuda.common.util.DistanceCalculator;
import com.pd.gilgeorigoreuda.image.service.ImageService;
import com.pd.gilgeorigoreuda.login.domain.OauthProvider;
import com.pd.gilgeorigoreuda.login.jwt.BearerTokenExtractor;
import com.pd.gilgeorigoreuda.login.jwt.JwtProvider;
import com.pd.gilgeorigoreuda.login.repository.MemberTokenRepository;
import com.pd.gilgeorigoreuda.login.service.LoginService;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.repository.MemberActiveInfoRepository;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.*;
import com.pd.gilgeorigoreuda.store.dto.request.BusinessDateRequest;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import com.pd.gilgeorigoreuda.store.service.StoreService;
import com.pd.gilgeorigoreuda.visit.repository.StoreVisitRecordRepository;
import com.pd.gilgeorigoreuda.visit.service.VisitService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@MockitoSettings
@Transactional
@ActiveProfiles("test")
public abstract class ServiceTest {

    // Mock 을 주입 받을 객체
    @InjectMocks
    protected StoreService storeService;

    @InjectMocks
    protected VisitService visitService;

    @InjectMocks
    protected LoginService loginService;

    // Mock 객체
    @Mock
    protected ApplicationEventPublisher publisher;

    @Mock
    protected StoreRepository storeRepository;

    @Mock
    protected StoreNativeQueryRepository storeNativeQueryRepository;

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected ImageService imageService;

    @Mock
    protected StoreVisitRecordRepository storeVisitRecordRepository;

    @Mock
    protected DistanceCalculator distanceCalculator;

    @Mock
    protected MemberTokenRepository memberTokenRepository;

    @Mock
    protected MemberActiveInfoRepository memberActiveInfoRepository;

    @Mock
    protected OauthProvider oauthProvider;

    @Mock
    protected JwtProvider jwtProvider;

    @Mock
    protected BearerTokenExtractor bearerTokenExtractor;

}
