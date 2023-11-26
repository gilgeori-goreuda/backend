package com.pd.gilgeorigoreuda.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

class DistanceCalculatorTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "37.53953321065698,127.0701553911476,37.53946339832465,127.07012986717491,10",
            "37.53985747116393,127.07032259086651,37.539823641553966,127.07039327766884,10"
    })
    @DisplayName("사용자의 위경도가 가게 반경 10m 이내에 있는 경우")
    void whenLatLngIsInBoundary(String coordinates) {
        String[] parts = coordinates.split(",");

        BigDecimal lat1 = new BigDecimal(parts[0]);
        BigDecimal lng1 = new BigDecimal(parts[1]);
        BigDecimal lat2 = new BigDecimal(parts[2]);
        BigDecimal lng2 = new BigDecimal(parts[3]);

        int boundary = Integer.parseInt(parts[4]);

        double calculatedDistance = DistanceCalculator.calculateDistance(lat1, lng1, lat2, lng2);

        assertThat(calculatedDistance).isLessThanOrEqualTo(boundary);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "37.53995012571436,127.06981350329157,37.53985746274046,127.07033673457978,10",
            "37.54062244128851,127.06797825636963,37.54013380030546,127.06771473855864,10"
    })
    @DisplayName("사용자의 위경도가 가게 반경 10m 이내에 없는 경우")
    void whenLatLngIsOutOfBoundary(String coordinates) {
        String[] parts = coordinates.split(",");

        BigDecimal lat1 = new BigDecimal(parts[0]);
        BigDecimal lng1 = new BigDecimal(parts[1]);
        BigDecimal lat2 = new BigDecimal(parts[2]);
        BigDecimal lng2 = new BigDecimal(parts[3]);

        int boundary = Integer.parseInt(parts[4]);

        double calculatedDistance = DistanceCalculator.calculateDistance(lat1, lng1, lat2, lng2);

        assertThat(calculatedDistance).isGreaterThan(boundary);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "37.539823633122026,127.0704074213757,37.53978528157852,127.07050639126659,10",
            "37.540058270712315,127.06977117284548,37.53999282272097,127.06998326806227,20"
    })
    @DisplayName("사용자의 위경도부터 가게의 위경도까지의 거리")
    void distanceBetweenMemberAndStore(String coordinates) {
        String[] parts = coordinates.split(",");

        BigDecimal lat1 = new BigDecimal(parts[0]);
        BigDecimal lng1 = new BigDecimal(parts[1]);
        BigDecimal lat2 = new BigDecimal(parts[2]);
        BigDecimal lng2 = new BigDecimal(parts[3]);

        int targetDistance = Integer.parseInt(parts[4]);

        double calculatedDistance = DistanceCalculator.calculateDistance(lat1, lng1, lat2, lng2);

        assertThat(calculatedDistance).isEqualTo(targetDistance);
    }

}