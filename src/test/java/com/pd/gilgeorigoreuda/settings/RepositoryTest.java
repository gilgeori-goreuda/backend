package com.pd.gilgeorigoreuda.settings;

import static org.springframework.test.annotation.DirtiesContext.*;

import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.search.repository.SearchRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;
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
@Import(JpaConfig.class)
@ActiveProfiles("test")
public abstract class RepositoryTest {

    @Autowired
    protected StoreRepository storeRepository;

    @Autowired
    protected SearchRepository searchRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected StoreNativeQueryRepository storeNativeQueryRepository;

}
