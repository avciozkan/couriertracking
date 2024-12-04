package com.ozkan.couriertracking.controller;

import com.ozkan.couriertracking.application.courierdistancequery.GetTotalTravelDistanceQuery;
import com.ozkan.couriertracking.application.couriertravelsquery.GetCourierTravelsQuery;
import com.ozkan.couriertracking.application.updatecourierlocationcommand.UpdateCourierLocationCommand;
import com.ozkan.couriertracking.infrastructure.mediator.base.Mediator;
import com.ozkan.couriertracking.presentation.controller.CourierController;
import com.ozkan.couriertracking.presentation.request.UpdateCourierLocationRequest;
import com.ozkan.couriertracking.presentation.response.CourierTravelsResponse;
import com.ozkan.couriertracking.presentation.response.TotalTravelDistanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourierControllerTest {
    @Mock
    private Mediator mediator;

    @InjectMocks
    private CourierController courierController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_UpdateCourierLocation() {
        // Arrange
        UpdateCourierLocationRequest request = new UpdateCourierLocationRequest(
                123L, 40.1234, 29.1234, 1733314236L
        );

        when(mediator.send(any(UpdateCourierLocationCommand.class))).thenReturn(null);

        // Act
        ResponseEntity<Void> response = courierController.updateCourierLocation(request);

        // Assert
        assertEquals(ResponseEntity.ok().build(), response);
        verify(mediator).send(any(UpdateCourierLocationCommand.class));
    }

    @Test
    void getCourierTravels_shouldReturnListOfTravels() {
        // given
        Long courierId = 123L;

        var response1 = CourierTravelsResponse.builder()
                .storeName("Store1").latitude(40.1234).longitude(29.1234).timestamp(System.currentTimeMillis()).build();
        var response2 = CourierTravelsResponse.builder()
                .storeName("Store2").latitude(41.1234).longitude(30.1234).timestamp(System.currentTimeMillis()).build();

        List<CourierTravelsResponse> mockResponse = List.of(
                response1, response2
        );
        when(mediator.send(any(GetCourierTravelsQuery.class))).thenReturn(mockResponse);

        // when
        ResponseEntity<List<CourierTravelsResponse>> response = courierController.getCourierTravels(courierId);

        // then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }

    @Test
    void getTotalDistance_shouldReturnTotalDistance() {
        // Arrange
        Long courierId = 123L;
        TotalTravelDistanceResponse mockResponse = TotalTravelDistanceResponse.builder().totalDistance("157.23km").build();
        when(mediator.send(any(GetTotalTravelDistanceQuery.class))).thenReturn(mockResponse);

        // Act
        ResponseEntity<TotalTravelDistanceResponse> response = courierController.getTotalDistance(courierId);

        // Assert
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }
}
