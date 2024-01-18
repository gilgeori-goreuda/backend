package com.pd.gilgeorigoreuda.settings;

import com.pd.gilgeorigoreuda.settings.builder.DataBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@RecordApplicationEvents
@Transactional
@ActiveProfiles("test")
public abstract class IntegrationTest {

    @Autowired
    protected DataBuilder dataBuilder;

    @Autowired
    protected ApplicationEvents applicationEvents;

}
