package com.ozkan.couriertracking.application.service;

public class DistanceCalculator {
    private static final double EARTH_RADIUS = 6371e3;

    public static double calculateDistance(double courierLatitude, double courierLongitude, double storeLatitude, double storeLongitude) {
        double latDistance = Math.toRadians(storeLatitude - courierLatitude);
        double lonDistance = Math.toRadians(storeLongitude - courierLongitude);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(courierLatitude)) * Math.cos(Math.toRadians(storeLatitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
