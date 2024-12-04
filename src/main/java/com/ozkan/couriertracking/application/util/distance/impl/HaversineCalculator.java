package com.ozkan.couriertracking.application.util.distance.impl;

import com.ozkan.couriertracking.application.util.distance.base.DistanceCalculator;

public class HaversineCalculator implements DistanceCalculator {
    @Override
    public double calculateDistance(double courierLatitude, double courierLongitude, double storeLatitude, double storeLongitude) {
        final int EARTH_RADIUS = 6371;
        double latDistance = Math.toRadians(storeLatitude - courierLatitude);
        double lonDistance = Math.toRadians(storeLongitude - courierLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(courierLatitude)) * Math.cos(Math.toRadians(storeLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c * 1000;
    }
}
