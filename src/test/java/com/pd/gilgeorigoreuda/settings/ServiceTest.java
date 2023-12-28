package com.pd.gilgeorigoreuda.settings;

import com.pd.gilgeorigoreuda.image.service.ImageService;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import com.pd.gilgeorigoreuda.store.service.StoreService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@MockitoSettings
@Transactional
@ActiveProfiles("test")
public abstract class ServiceTest {

    // Mock 을 주입 받을 객체
    @InjectMocks
    protected StoreService storeService;

    // Mock 객체
    @Mock
    protected StoreRepository storeRepository;

    @Mock
    protected StoreNativeQueryRepository storeNativeQueryRepository;

    @Mock
    protected MemberRepository memberRepository;

    @Mock
    protected ImageService imageService;

}
