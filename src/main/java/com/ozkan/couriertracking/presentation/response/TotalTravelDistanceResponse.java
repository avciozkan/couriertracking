package com.ozkan.couriertracking.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TotalTravelDistanceResponse {
    private String totalDistance;
}
