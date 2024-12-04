package com.ozkan.couriertracking.application.util.distance.base;

public interface DistanceCalculator {
    double calculateDistance(double courierLatitude, double courierLongitude, double storeLatitude, double storeLongitude);
}