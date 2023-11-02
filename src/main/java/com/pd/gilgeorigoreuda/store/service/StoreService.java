package com.pd.gilgeorigoreuda.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
import com.pd.gilgeorigoreuda.store.dto.response.UpdateStoreResponse;
import com.pd.gilgeorigoreuda.store.exception.AlreadyExistInBoundaryException;
import com.pd.gilgeorigoreuda.store.exception.NoSuchStoreException;
import com.pd.gilgeorigoreuda.store.repository.StoreNativeQueryRepository;
import com.pd.gilgeorigoreuda.store.repository.StoreRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

	private final StoreRepository storeRepository;
	private final StoreNativeQueryRepository storeNativeQueryRepository;

	private static final Integer BOUNDARY = 10;

	@Transactional
	public StoreCreateResponse saveStore(final Long memberId, final StoreCreateRequest request) {
		Double lat = request.getLat();
		Double lng = request.getLng();

		checkIsAlreadyExistInBoundary(lat, lng);

		Store store = request.toEntity(memberId);

		List<FoodCategory> foodCategories = request
			.getFoodCategories()
			.toEntities();

		store.addFoodCategories(foodCategories);

		Store saveStore = storeRepository.save(store);

		return StoreCreateResponse.of(saveStore.getId());
	}

	public StoreResponse getStore(final Long storeId) {
		Store store = findStore(storeId);

		return StoreResponse.of(store);
	}

	@Transactional
	public UpdateStoreResponse updateStore(final Long memberId, final Long storeId, final StoreUpdateRequest request) {
		Store storeForUpdate = findStore(storeId);

		storeForUpdate.updateBasicInfo();

		return null;
	}

	@Transactional
	public void deleteStore(final Long memberId, final Long storeId) {

	}

	private void checkIsAlreadyExistInBoundary(final Double lat, final Double lng) {
		Optional<Long> isAlreadyExistInBoundary = storeNativeQueryRepository.isAlreadyExistInBoundary(lat, lng, BOUNDARY);

		if (isAlreadyExistInBoundary.isPresent()) {
			throw new AlreadyExistInBoundaryException();
		}
	}

	private Store findStore(Long storeId) {
		return storeRepository.findByStoreId(storeId)
			.orElseThrow(NoSuchStoreException::new);
	}

}
