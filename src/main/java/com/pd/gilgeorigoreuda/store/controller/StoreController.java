package com.pd.gilgeorigoreuda.store.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreUpdateResponse;
import com.pd.gilgeorigoreuda.store.service.StoreService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
public class StoreController {

	private final StoreService storeService;

	// todo: 권한 확인
	@PostMapping
	public ResponseEntity<Void> createStore(
		// todo: 유저 정보
		@Valid @RequestBody final StoreCreateRequest storeCreateRequest
	) {
		StoreCreateResponse storeCreateResponse = storeService.saveStore(1L, storeCreateRequest);

		return ResponseEntity
			.created(URI.create("/api/v1/stores/" + storeCreateResponse.getId()))
			.build();
	}

	@GetMapping("/{storeId}")
	public ResponseEntity<StoreResponse> getStore(
		// todo: 유저 정보
		@PathVariable final Long storeId
	) {
		StoreResponse storeResponse = storeService.getStore(storeId);

		return ResponseEntity
			.ok()
			.body(storeResponse);
	}

	// todo: 권한 확인
	@PutMapping("/{storeId}")
	public ResponseEntity<StoreUpdateResponse> updateStore(
		// todo: 유저 정보
		@Valid @RequestBody final StoreUpdateRequest storeUpdateRequest,
		@PathVariable final Long storeId
	) {
		StoreUpdateResponse storeUpdateResponse = storeService.updateStore(1L, storeId, storeUpdateRequest);

		return ResponseEntity
			.ok()
			.body(storeUpdateResponse);
	}

	// todo: 권한 확인
	@DeleteMapping("/{storeId}")
	public ResponseEntity<Void> deleteStore(
		// todo: 유저 정보
		@PathVariable final Long storeId
	) {
		storeService.deleteStore(1L, storeId);

		return ResponseEntity
			.noContent()
			.build();
	}

}
