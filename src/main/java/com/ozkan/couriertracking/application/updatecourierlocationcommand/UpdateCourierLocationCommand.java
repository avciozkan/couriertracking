package com.ozkan.couriertracking.application.updatecourierlocationcommand;

import com.ozkan.couriertracking.infrastructure.mediator.base.Request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCourierLocationCommand implements Request<Void> {
    private Long courierId;
    private Double longitude;
    private Double latitude;
    private Long timestamp;
}
