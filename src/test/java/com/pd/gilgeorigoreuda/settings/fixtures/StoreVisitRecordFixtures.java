package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.store.domain.entity.StoreVisitRecord;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;

public class StoreVisitRecordFixtures {

    public static StoreVisitRecord KIMS_BUNGEOPPANG_VISIT_RECORD() {
        return StoreVisitRecord.builder()
                .id(1L)
                .walkingDistance(100)
                .isVisited(false)
                .store(BUNGEOPPANG())
                .member(KIM())
                .build();
    }

    public static StoreVisitRecord LEES_ODENG_VISIT_RECORD() {
        return StoreVisitRecord.builder()
                .id(2L)
                .walkingDistance(200)
                .isVisited(false)
                .store(ODENG())
                .member(LEE())
                .build();
    }

}
