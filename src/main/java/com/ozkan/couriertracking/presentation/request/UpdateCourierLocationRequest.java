package com.ozkan.couriertracking.presentation.request;

import com.ozkan.couriertracking.application.updatecourierlocationcommand.UpdateCourierLocationCommand;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCourierLocationRequest {
    private Long courierId;
    private Double longitude;
    private Double latitude;
    private Long timestamp;

    public UpdateCourierLocationCommand toUpdateCourierLocationCommand() {
        return UpdateCourierLocationCommand.builder()
                .courierId(courierId)
                .latitude(latitude)
                .longitude(longitude)
                .timestamp(timestamp)
                .build();
    }
}
