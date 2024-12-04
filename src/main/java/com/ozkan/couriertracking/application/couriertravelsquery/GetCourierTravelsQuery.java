package com.ozkan.couriertracking.application.couriertravelsquery;

import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.mediator.base.Request;
import com.ozkan.couriertracking.presentation.response.CourierTravelsResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetCourierTravelsQuery implements Request<List<CourierTravelsResponse>> {
    private Long courierId;

    public List<CourierTravelsResponse> toCourierTravelsResponses(List<Courier> courierLogs) {
        return courierLogs.stream().map(log -> CourierTravelsResponse.builder()
                .storeName(log.getStoreName())
                .latitude(log.getLatitude())
                .longitude(log.getLongitude())
                .timestamp(log.getTimestamp())
                .build()).toList();
    }
}
