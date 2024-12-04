package com.ozkan.couriertracking.application.courierdistancequery;

import com.ozkan.couriertracking.infrastructure.mediator.base.Request;
import com.ozkan.couriertracking.presentation.response.TotalTravelDistanceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTotalTravelDistanceQuery implements Request<TotalTravelDistanceResponse> {
    private Long courierId;


    public TotalTravelDistanceResponse toTotalTravelDistance(String totalDistance) {
        return TotalTravelDistanceResponse.builder()
                .totalDistance(totalDistance)
                .build();
    }
}
