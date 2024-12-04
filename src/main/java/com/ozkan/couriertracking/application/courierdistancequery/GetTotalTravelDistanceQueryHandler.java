package com.ozkan.couriertracking.application.courierdistancequery;

import com.ozkan.couriertracking.application.enums.DistanceType;
import com.ozkan.couriertracking.application.exception.CourierNotFoundException;
import com.ozkan.couriertracking.application.util.distance.DistanceCalculatorFactory;
import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.mediator.base.RequestHandler;
import com.ozkan.couriertracking.infrastructure.repository.CourierRepository;
import com.ozkan.couriertracking.presentation.response.TotalTravelDistanceResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GetTotalTravelDistanceQueryHandler")
public class GetTotalTravelDistanceQueryHandler implements RequestHandler<GetTotalTravelDistanceQuery, TotalTravelDistanceResponse> {
    private final CourierRepository courierRepository;

    public GetTotalTravelDistanceQueryHandler(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    @Cacheable(value = "totalDistances", key = "#query.courierId", cacheManager = "cacheManager")
    public TotalTravelDistanceResponse handle(GetTotalTravelDistanceQuery query) {
        List<Courier> courierLogs = courierRepository
                .findByCourierIdOrderByTimestampAsc(query.getCourierId());
        if (courierLogs.isEmpty()) {
            throw new CourierNotFoundException("No logs found for courier with ID: " + query.getCourierId());
        }
        double totalDistance = calculateTotalDistance(courierLogs);
        totalDistance = totalDistance / 1000;

        String totalDistanceStr = String.format("%.2f km", totalDistance);
        return query.toTotalTravelDistance(totalDistanceStr);
    }

    private double calculateTotalDistance(List<Courier> courierLogs) {
        double totalDistance = 0.0;
        var distanceCalculator = DistanceCalculatorFactory.getCalculator(DistanceType.HAVERSINE);
        for (int i = 1; i < courierLogs.size(); i++) {
            Courier previousLog = courierLogs.get(i - 1);
            Courier currentLog = courierLogs.get(i);
            totalDistance += distanceCalculator.calculateDistance(
                    previousLog.getLatitude(), previousLog.getLongitude(),
                    currentLog.getLatitude(), currentLog.getLongitude()
            );
        }
        return totalDistance;
    }
}
