package com.ozkan.couriertracking.presentation.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourierTravelsResponse {
    private String storeName;
    private Double latitude;
    private Double longitude;
    private Long timestamp;
}
