package com.ozkan.couriertracking.util;

import com.ozkan.couriertracking.application.util.distance.impl.HaversineCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HaversineDistanceCalculatorTest {

    @Test
    public void testCalculateDistance_ValidCoordinates() {
        HaversineCalculator calculator = new HaversineCalculator();

        double lat1 = 40.7128;
        double lon1 = -74.0060;
        double lat2 = 34.0522;
        double lon2 = -118.2437;

        double distance = calculator.calculateDistance(lat1, lon1, lat2, lon2);

        assertTrue(distance > 0);
    }

    @Test
    public void testCalculateDistance_SameCoordinates() {
        HaversineCalculator calculator = new HaversineCalculator();

        double lat = 40.7128;
        double lon = -74.0060;

        double distance = calculator.calculateDistance(lat, lon, lat, lon);

        assertEquals(0, distance, 0.001);
    }
}
