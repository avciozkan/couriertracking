package com.ozkan.couriertracking.util;

import com.ozkan.couriertracking.application.enums.DistanceType;
import com.ozkan.couriertracking.application.util.distance.DistanceCalculatorFactory;
import com.ozkan.couriertracking.application.util.distance.base.DistanceCalculator;
import com.ozkan.couriertracking.application.util.distance.impl.HaversineCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DistanceCalculatorFactoryTest {

    @Test
    public void testGetHaversineCalculator() {
        DistanceCalculator calculator = DistanceCalculatorFactory.getCalculator(DistanceType.HAVERSINE);
        assertNotNull(calculator);
        assertInstanceOf(HaversineCalculator.class, calculator);
    }
}
