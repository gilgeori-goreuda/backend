package com.pd.gilgeorigoreuda.store.dto.request;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.pd.gilgeorigoreuda.store.exception.BusinessDateResponseReflectionException;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BusinessDateRequest {

	private Boolean monday = true;
	private Boolean tuesday = true;
	private Boolean wednesday = true;
	private Boolean thursday = true;
	private Boolean friday = true;
	private Boolean saturday = true;
	private Boolean sunday = true;

	public static BusinessDateRequest of(final String availableDatesString) {
		final BusinessDateRequest businessDateRequest = new BusinessDateRequest();
		setFields(businessDateRequest, availableDatesString);

		return businessDateRequest;
	}

	private static void setFields(final BusinessDateRequest businessDateRequest, final String availableDatesString) {
		final Field[] fields = businessDateRequest.getClass().getDeclaredFields();
		final List<String> availableDays = getEnabledFieldNames(availableDatesString);

		Arrays.stream(fields)
			.filter(field -> !availableDays.contains(field.getName()))
			.forEach(field -> setFalse(businessDateRequest, field));
	}

	private static List<String> getEnabledFieldNames(final String availableDatesString) {
		return Arrays.stream(availableDatesString.split(","))
			.map(String::trim)
			.map(String::toLowerCase)
			.toList();
	}

	private static void setFalse(final BusinessDateRequest businessDateRequest, final Field targetField) {
		try {
			targetField.setAccessible(true);
			targetField.set(businessDateRequest, Boolean.FALSE);
		} catch (IllegalAccessException e) {
			throw new BusinessDateResponseReflectionException();
		}
	}

	@Override
	public String toString() {
		final Field[] fields = this.getClass().getDeclaredFields();
		final List<String> availableDates = getAvailableDates(fields);

		return String.join(",", availableDates);
	}

	private List<String> getAvailableDates(final Field[] fields) {
		final List<String> availableDates = new ArrayList<>();

		for (final Field field : fields) {
			field.setAccessible(true);
			final Boolean value = getValue(field);
			if (Boolean.TRUE.equals(value)) {
				availableDates.add(field.getName());
			}
		}

		return availableDates;
	}

	private Boolean getValue(final Field field) {
		try {
			return (Boolean) field.get(this);
		} catch (IllegalAccessException e) {
			throw new BusinessDateResponseReflectionException();
		}
	}
}