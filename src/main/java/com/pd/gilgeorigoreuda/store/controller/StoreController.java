package com.pd.gilgeorigoreuda.store.controller;

import java.math.BigDecimal;
import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pd.gilgeorigoreuda.store.dto.request.StoreCreateRequest;
import com.pd.gilgeorigoreuda.store.dto.request.StoreUpdateRequest;
import com.pd.gilgeorigoreuda.store.dto.response.StoreCreateResponse;
import com.pd.gilgeorigoreuda.store.dto.response.StoreResponse;
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
		@PathVariable final Long storeId,
		@RequestParam(required = false, name = "lat") final BigDecimal lat,
		@RequestParam(required = false, name = "lng") final BigDecimal lng
	) {
		StoreResponse storeResponse = storeService.getStore(storeId, lat, lng);

		return ResponseEntity
			.ok()
			.body(storeResponse);
	}

	// todo: 권한 확인
	@PutMapping("/{storeId}")
	public ResponseEntity<Void> updateStore(
		// todo: 유저 정보
		@Valid @RequestBody final StoreUpdateRequest storeUpdateRequest,
		@PathVariable final Long storeId
	) {
		storeService.updateStore(1L, storeId, storeUpdateRequest);

		return ResponseEntity
				.noContent()
				.build();
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
