package com.ozkan.couriertracking.application.couriertravelsquery;

import com.ozkan.couriertracking.application.exception.CourierNotFoundException;
import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.mediator.base.RequestHandler;
import com.ozkan.couriertracking.infrastructure.repository.CourierRepository;
import com.ozkan.couriertracking.presentation.response.CourierTravelsResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("GetCourierTravelsQueryHandler")
public class GetCourierTravelsQueryHandler implements RequestHandler<GetCourierTravelsQuery, List<CourierTravelsResponse>> {
    private CourierRepository courierRepository;

    public GetCourierTravelsQueryHandler(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    @Cacheable(value = "courierLogs", key = "#query.courierId", cacheManager = "cacheManager")
    public List<CourierTravelsResponse> handle(GetCourierTravelsQuery query) {
        List<Courier> courierLogs = courierRepository
                .findByCourierIdOrderByTimestampAsc(query.getCourierId());

        if (courierLogs.isEmpty()) {
            throw new CourierNotFoundException("No logs found for courier with ID: " + query.getCourierId());
        }

        return query.toCourierTravelsResponses(courierLogs);
    }
}
