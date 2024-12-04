package com.ozkan.couriertracking.presentation.controller;

import com.ozkan.couriertracking.application.courierdistancequery.GetTotalTravelDistanceQuery;
import com.ozkan.couriertracking.application.couriertravelsquery.GetCourierTravelsQuery;
import com.ozkan.couriertracking.infrastructure.mediator.base.Mediator;
import com.ozkan.couriertracking.presentation.request.UpdateCourierLocationRequest;
import com.ozkan.couriertracking.presentation.response.CourierTravelsResponse;
import com.ozkan.couriertracking.presentation.response.TotalTravelDistanceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/couriers")
@Validated
@Tag(name = "Couriers", description = "Courier related operations")
public class CourierController {

    private final Mediator mediator;

    public CourierController(Mediator mediator) {
        this.mediator = mediator;
    }

    @Operation(summary = "Get Total Travel Distance", description = "Returns the total distance traveled by a courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total distance returns"),
            @ApiResponse(responseCode = "404", description = "Courier not found")
    })
    @GetMapping("/{courierId}/total-distance")
    public ResponseEntity<TotalTravelDistanceResponse> getTotalDistance(@PathVariable Long courierId) {
        GetTotalTravelDistanceQuery query = new GetTotalTravelDistanceQuery(courierId);
        var distance = mediator.send(query);
        return ResponseEntity.ok(distance);
    }

    @Operation(summary = "Update Courier Location", description = "Updates location of Courier's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Courier logging successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid courier data"),
            @ApiResponse(responseCode = "409", description = "Duplicate entry for the store within the last minute.")
    })
    @PutMapping("/update-location")
    public ResponseEntity<Void> updateCourierLocation(
            @Valid @RequestBody UpdateCourierLocationRequest request) {
        mediator.send(request.toUpdateCourierLocationCommand());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @Operation(summary = "Get Travels of Courier", description = "Returns the all travels of courier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All travels successfully"),
            @ApiResponse(responseCode = "404", description = "Courier not found")
    })
    @GetMapping("/{courierId}/travels")
    public ResponseEntity<List<CourierTravelsResponse>> getCourierTravels(@PathVariable Long courierId) {
        GetCourierTravelsQuery query = new GetCourierTravelsQuery(courierId);
        var logs = mediator.send(query);
        return ResponseEntity.ok(logs);
    }
}
