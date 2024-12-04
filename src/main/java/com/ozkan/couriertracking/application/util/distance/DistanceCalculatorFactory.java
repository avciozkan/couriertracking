package com.ozkan.couriertracking.application.util.distance;

import com.ozkan.couriertracking.application.enums.DistanceType;
import com.ozkan.couriertracking.application.util.distance.base.DistanceCalculator;
import com.ozkan.couriertracking.application.util.distance.impl.HaversineCalculator;

import java.util.Map;
import java.util.Objects;

public class DistanceCalculatorFactory {
    private static final Map<DistanceType, DistanceCalculator> STRATEGIES = Map.of(
            DistanceType.HAVERSINE, new HaversineCalculator()
    );

    public static DistanceCalculator getCalculator(DistanceType type) {
        DistanceCalculator strategy = STRATEGIES.get(Objects.requireNonNull(type));
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported distance calculation strategy: " + type);
        }
        return strategy;
    }
}
