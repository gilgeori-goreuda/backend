package com.pd.gilgeorigoreuda.store.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.pd.gilgeorigoreuda.common.util.DistanceCalculator;
import com.pd.gilgeorigoreuda.image.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pd.gilgeorigoreuda.member.domain.entity.Member;
import com.pd.gilgeorigoreuda.member.exception.NoSuchMemberException;
import com.pd.gilgeorigoreuda.member.repository.MemberRepository;
import com.pd.gilgeorigoreuda.store.domain.entity.FoodCategory;
import com.pd.gilgeorigoreuda.store.domain.entity.Store;
import com.pd.gilgeorigoreuda.store.domain.entity.StreetAddress;
import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
import com.pd.gilgeorigoreuda.store.exception.AlreadyExistInBoundaryException;
import com.pd.gilgeorigoreuda.store.exception.NoOwnerMemberException;
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
	private final MemberRepository memberRepository;
	private final ImageService imageService;

	private static final Integer BOUNDARY = 10;

	@Transactional(readOnly = false)
	public StoreCreateResponse saveStore(final Long memberId, final StoreCreateRequest storeCreateRequest) {
		StreetAddress streetAddress = StreetAddress.of(storeCreateRequest.getStreetAddress());

		checkIsAlreadyExistInBoundary(
				storeCreateRequest.getLat(),
				storeCreateRequest.getLng(),
				streetAddress.getLargeAddress(),
				streetAddress.getMediumAddress()
		);

		Store store = storeCreateRequest.toEntity(memberId);

		List<FoodCategory> foodCategories = storeCreateRequest.getFoodCategories().toEntities();

		store.addFoodCategories(foodCategories);

		Store saveStore = storeRepository.save(store);

		return StoreCreateResponse.of(saveStore.getId());
	}

	@Transactional
	public void updateStore(final Long memberId, final Long storeId, final StoreUpdateRequest storeUpdateRequest) {
		Store storeForUpdate = findStoreWithMemberAndCategories(storeId);
		Member member = findMember(memberId);

		StreetAddress streetAddress = StreetAddress.of(storeUpdateRequest.getStreetAddress());

		checkIsAlreadyExistInBoundary(
				storeUpdateRequest.getLat(),
				storeUpdateRequest.getLng(),
				streetAddress.getLargeAddress(),
				streetAddress.getMediumAddress()
		);

		List<FoodCategory> foodCategories = storeUpdateRequest.getFoodCategories().toEntities();

		storeForUpdate.updateStoreInfo(
				storeUpdateRequest.getName(),
				storeUpdateRequest.getStoreType(),
				storeUpdateRequest.getOpenTime(),
				storeUpdateRequest.getCloseTime(),
				storeUpdateRequest.getPurchaseType(),
				storeUpdateRequest.getBusinessDates(),
				storeUpdateRequest.getImageUrl(),
				storeUpdateRequest.getLat(),
				storeUpdateRequest.getLng(),
				storeUpdateRequest.getStreetAddress(),
				member.getNickname()
		);

		storeForUpdate.addFoodCategories(foodCategories);

		deleteOriginalImage(storeUpdateRequest, storeForUpdate);
	}

	@Transactional
	public void deleteStore(final Long memberId, final Long storeId) {
		Store storeForDelete = findStoreWithMember(storeId);

		if (!storeForDelete.isOwner(memberId)) {
			throw new NoOwnerMemberException();
		}

		storeRepository.deleteById(storeForDelete.getId());
		deleteImage(storeForDelete);
	}

	private void deleteOriginalImage(final StoreUpdateRequest storeUpdateRequest, final Store storeForUpdate) {
		if (!storeForUpdate.isSameImage(storeUpdateRequest.getImageUrl())) {
			imageService.deleteSingleImage(storeForUpdate.getImageUrl());
		}
	}

	private void deleteImage(final Store store) {
		imageService.deleteSingleImage(store.getImageUrl());
	}

	@Transactional(readOnly = true)
	public StoreResponse getStore(final Long storeId, final BigDecimal memberlat, final BigDecimal memberlng) {
		Store store = findStoreWithMemberAndCategories(storeId);

		int distanceFromMember = getDistanceBetweenStoreAndMember(memberlat, memberlng, store.getLat(), store.getLng());

		return StoreResponse.of(store, distanceFromMember);
	}

	private void checkIsAlreadyExistInBoundary(final BigDecimal lat, final BigDecimal lng, final String largeAddress, final String mediumAddress) {
		Optional<Long> isAlreadyExistInBoundary = storeNativeQueryRepository.isAlreadyExistInBoundary(lat, lng, largeAddress, mediumAddress, BOUNDARY);

		if (isAlreadyExistInBoundary.isPresent()) {
			throw new AlreadyExistInBoundaryException();
		}
	}

	private Store findStoreWithMemberAndCategories(Long storeId) {
		return storeRepository.findStoreWithMemberAndCategories(storeId)
			.orElseThrow(NoSuchStoreException::new);
	}

	private Member findMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(NoSuchMemberException::new);
	}

	private Store findStoreWithMember(Long storeId) {
		return storeRepository.findStoreWithMember(storeId)
			.orElseThrow(NoSuchStoreException::new);
	}

	private static int getDistanceBetweenStoreAndMember(final BigDecimal memberLat, final BigDecimal memberLng,
														final BigDecimal targetStoreLat, final BigDecimal targetStoreLng) {
		return DistanceCalculator.calculateDistance(memberLat, memberLng, targetStoreLat, targetStoreLng);
	}

}
