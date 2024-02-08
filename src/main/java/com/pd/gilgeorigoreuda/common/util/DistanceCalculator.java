package com.pd.gilgeorigoreuda.common.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DistanceCalculator {

    private static final int EARTH_RADIUS = 6371000;
    public static final double WALKING_SPEED = 5000.0 / 3600.0;
    public static final int ONE_HOUR = 3600;

    public int getDistance(
            final BigDecimal startLatitude,
            final BigDecimal startLongitude,
            final BigDecimal destinationLatitude,
            final BigDecimal destinationLongitude
    ) {
        // 위도와 경도를 라디안 값으로 변환
        double startLatitudeToRadian = Math.toRadians(startLatitude.doubleValue());
        double startLongitudeToRadian = Math.toRadians(startLongitude.doubleValue());
        double destinationLatitudeToRadian = Math.toRadians(destinationLatitude.doubleValue());
        double destinationLongitudeToRadian = Math.toRadians(destinationLongitude.doubleValue());

        // 위도와 경도의 차이를 계산
        double delLatitude = destinationLatitudeToRadian - startLatitudeToRadian;
        double delLongitude = destinationLongitudeToRadian - startLongitudeToRadian;

        double tempVal = Math.sin(delLatitude / 2) * Math.sin(delLatitude / 2) +
                Math.cos(startLatitudeToRadian) * Math.cos(destinationLatitudeToRadian) *
                        Math.sin(delLongitude / 2) * Math.sin(delLongitude / 2);

        // 중심각을 계산
        double centralAngle = 2 * Math.atan2(Math.sqrt(tempVal), Math.sqrt(1 - tempVal));

        // 두 지점 사이의 거리를 계산 (지구 반지름과 중심각의 곱)
        return (int) Math.round(EARTH_RADIUS * centralAngle);
    }

    public int getApproximateWalkingTime(final int distance) {
        // 거리를 보행 속도로 나누어 보행 시간 (초)을 계산
        double walkingTimeSeconds = (double) distance / WALKING_SPEED;

        // 보행 시간 (초)을 시간으로 변환
        return (int) Math.ceil(walkingTimeSeconds / ONE_HOUR);
    }

}
