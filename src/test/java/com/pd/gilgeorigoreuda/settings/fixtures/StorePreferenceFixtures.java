package com.pd.gilgeorigoreuda.settings.fixtures;

import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;

import static com.pd.gilgeorigoreuda.settings.fixtures.MemberFixtures.*;
import static com.pd.gilgeorigoreuda.settings.fixtures.StoreFixtures.*;
import static com.pd.gilgeorigoreuda.store.domain.entity.StorePreferenceType.*;

public class StorePreferenceFixtures {

    public static StorePreference BUNGEOPPANG_PREFERENCE_PREFERRED() {
        return StorePreference.builder()
                .store(BUNGEOPPANG())
                .member(LEE())
                .preferenceType(PREFERRED)
                .build();
    }

    public static StorePreference BUNGEOPPANG_PREFERENCE_NONE() {
        return StorePreference.builder()
                .store(BUNGEOPPANG())
                .member(LEE())
                .preferenceType(NONE)
                .build();
    }

    public static StorePreference BUNGEOPPANG_PREFERENCE_NOT_PREFERRED() {
        return StorePreference.builder()
                .store(BUNGEOPPANG())
                .member(LEE())
                .preferenceType(NOT_PREFERRED)
                .build();
    }

    public static StorePreference ODENG_PREFERENCE_PREFERRED() {
        return StorePreference.builder()
                .store(ODENG())
                .member(KIM())
                .preferenceType(PREFERRED)
                .build();
    }

    public static StorePreference ODENG_PREFERENCE_NONE() {
        return StorePreference.builder()
                .store(ODENG())
                .member(KIM())
                .preferenceType(NONE)
                .build();
    }

    public static StorePreference ODENG_PREFERENCE_NOT_PREFERRED() {
        return StorePreference.builder()
                .store(ODENG())
                .member(KIM())
                .preferenceType(NOT_PREFERRED)
                .build();
    }

}
