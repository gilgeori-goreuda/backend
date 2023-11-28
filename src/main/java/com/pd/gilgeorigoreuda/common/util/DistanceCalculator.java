package com.pd.gilgeorigoreuda.common.util;

import java.math.BigDecimal;

public class DistanceCalculator {

    private static final int EARTH_RADIUS = 6371000;
    public static final double WALKING_SPEED = 5000.0 / 3600.0;
    public static final int ONE_HOUR = 3600;

    public static int calculateDistance(
            final BigDecimal memberLat,
            final BigDecimal memberLng,
            final BigDecimal storeLat,
            final BigDecimal storeLng
    ) {
        double lat1 = Math.toRadians(memberLat.doubleValue());
        double lng1 = Math.toRadians(memberLng.doubleValue());
        double lat2 = Math.toRadians(storeLat.doubleValue());
        double lng2 = Math.toRadians(storeLng.doubleValue());

        double dLat = lat2 - lat1;
        double dLon = lng2 - lng1;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon/2) * Math.sin(dLon/2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (int) Math.round(EARTH_RADIUS * c);
    }

    public static int calculateApproximateWalkingTime(final int distance) {
        double walkingTimeSeconds = (double) distance / WALKING_SPEED;

        return (int) Math.ceil(walkingTimeSeconds / ONE_HOUR);
    }

}
