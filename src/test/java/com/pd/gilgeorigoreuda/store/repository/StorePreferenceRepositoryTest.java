package com.pd.gilgeorigoreuda.store.repository;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.StorePreference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StorePreferenceRepositoryTest {

    @Autowired
    StorePreferenceRepository storePreferenceRepository;

    @Test
    void asd() {
        List<StorePreference> myPreferredStores = storePreferenceRepository.findMyPreferredStores(1L);
        for (StorePreference myPreferredStore : myPreferredStores) {
            List<FoodCategory> categories = myPreferredStore.getStore().getCategories();
            for (FoodCategory category : categories) {
                System.out.println(category.getFoodType().getFoodName());
            }
        }
    }

}