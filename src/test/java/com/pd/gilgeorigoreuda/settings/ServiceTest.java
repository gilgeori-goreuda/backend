package com.pd.gilgeorigoreuda.settings;

import com.pd.gilgeorigoreuda.image.service.ImageService;
import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.*;
import com.pd.gilgeorigoreuda.store.dto.request.BusinessDateRequest;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import com.pd.gilgeorigoreuda.store.service.StoreService;
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


    protected static final Member MEMBER = Member.builder()
            .id(1L)
            .nickname("nickname")
            .profileImageUrl("profileImageUrl")
            .socialId("socialId")
            .build();

    protected static final FoodCategory FOOD_CATEGORY1 = FoodCategory.builder()
            .id(1L)
            .foodType(FoodType.of("붕어빵").getFoodName())
            .build();

    protected static final FoodCategory FOOD_CATEGORY2 = FoodCategory.builder()
            .id(1L)
            .foodType(FoodType.of("호떡").getFoodName())
            .build();

    protected static final Store STORE = Store.builder()
            .id(1L)
            .name("붕어빵 가게")
            .storeType(StoreType.of("포장마차"))
            .openTime(LocalTime.of(9, 0))
            .closeTime(LocalTime.of(23, 0))
            .purchaseType(PurchaseType.of("현금"))
            .imageUrl("https://image.com")
            .businessDate(BusinessDateRequest.of("monday,tuesday,wednesday,thursday,friday,saturday,sunday").toString())
            .lat(BigDecimal.valueOf(37.123456))
            .lng(BigDecimal.valueOf(127.123456))
            .streetAddress(StreetAddress.of("서울특별시 강남구 언주로1"))
            .member(MEMBER)
            .foodCategories(new ArrayList<>(List.of(FOOD_CATEGORY1, FOOD_CATEGORY2)))
            .build();

}
