package com.pd.gilgeorigoreuda.store.domain.entity;

import java.util.Arrays;

import com.pd.gilgeorigoreuda.store.exception.NoSuchPurchaseTypeException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PurchaseType {

	CARD("카드"),
	CASH("현금"),
	ACCOUNT_TRANSFER("계좌이체"),
	;

	private final String purchaseTypeName;

	private static PurchaseType of(String purchaseType) {
		return Arrays.stream(values())
			.filter(pt -> pt.getPurchaseTypeName().equals(purchaseType.toUpperCase()))
			.findFirst()
			.orElseThrow(NoSuchPurchaseTypeException::new);
	}

}
