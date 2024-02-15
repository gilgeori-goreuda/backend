package com.pd.gilgeorigoreuda.settings;

import static org.springframework.test.annotation.DirtiesContext.*;

import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.search.repository.SearchRepository;
import com.pd.gilgeorigoreuda.settings.builder.BuilderSupporter;
import com.pd.gilgeorigoreuda.settings.builder.DataBuilder;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
import com.pd.gilgeorigoreuda.visit.repository.StoreVisitRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.pd.gilgeorigoreuda.common.config.JpaConfig;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// 자동으로 감지되거나 포함되지 않는 특정 구성 또는 구성 요소를 테스트 컨텍스트로 가져오려면 @Import 어노테이션을 사용
@Import({JpaConfig.class, DataBuilder.class, BuilderSupporter.class})
@ActiveProfiles("test")
public abstract class RepositoryTest {

    @Autowired
    protected DataBuilder dataBuilder;

    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    protected SearchRepository searchRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected StoreNativeQueryRepository storeNativeQueryRepository;

    @Autowired
    protected StoreVisitRecordRepository storeVisitRecordRepository;

}
