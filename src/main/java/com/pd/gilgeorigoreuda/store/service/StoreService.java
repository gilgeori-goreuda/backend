package com.pd.gilgeorigoreuda.store.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
import com.pd.gilgeorigoreuda.store.dto.response.StoreUpdateResponse;
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

	private static final Integer BOUNDARY = 10;

	@Transactional
	public StoreCreateResponse saveStore(final Long memberId, final StoreCreateRequest request) {
		BigDecimal lat = request.getLat();
		BigDecimal lng = request.getLng();
		StreetAddress streetAddress = StreetAddress.of(request.getStreetAddress());
		String largeAddress = streetAddress.getLargeAddress();
		String mediumAddress = streetAddress.getMediumAddress();

		checkIsAlreadyExistInBoundary(lat, lng, largeAddress, mediumAddress);

		Store store = request.toEntity(memberId);

		List<FoodCategory> foodCategories = request
			.getFoodCategories()
			.toEntities();

		store.addFoodCategories(foodCategories);

		Store saveStore = storeRepository.save(store);

		return StoreCreateResponse.of(saveStore.getId());
	}

	public StoreResponse getStore(final Long storeId) {
		Store store = findStoreWithMemberAndCategories(storeId);

		return StoreResponse.of(store);
	}

	@Transactional
	public StoreUpdateResponse updateStore(final Long memberId, final Long storeId, final StoreUpdateRequest request) {
		Store storeForUpdate = findStoreWithMemberAndCategories(storeId);
		Member member = findMember(memberId);

		StreetAddress streetAddress = StreetAddress.of(request.getStreetAddress());
		BigDecimal lat = request.getLat();
		BigDecimal lng = request.getLng();
		String largeAddress = streetAddress.getLargeAddress();
		String mediumAddress = streetAddress.getMediumAddress();

		checkIsAlreadyExistInBoundary(lat, lng, largeAddress, mediumAddress);

		List<FoodCategory> foodCategories = request
			.getFoodCategories()
			.toEntities();

		storeForUpdate.updateBasicInfo(
			request.getName(),
			request.getStoreType(),
			request.getOpenTime(),
			request.getCloseTime(),
			request.getPurchaseType(),
			request.getBusinessDates(),
			request.getLat(),
			request.getLng(),
			request.getStreetAddress(),
			member.getNickname()
		);

		storeForUpdate.addFoodCategories(foodCategories);

		return StoreUpdateResponse.of(storeForUpdate);
	}

	@Transactional
	public void deleteStore(final Long memberId, final Long storeId) {
		Store storeForDelete = findStoreWithMember(storeId);

		if (!storeForDelete.isOwner(memberId)) {
			throw new NoOwnerMemberException();
		}

		storeRepository.deleteById(storeForDelete.getId());
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

}
