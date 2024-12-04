package com.ozkan.couriertracking.handler;

import com.ozkan.couriertracking.application.courierdistancequery.GetTotalTravelDistanceQuery;
import com.ozkan.couriertracking.application.courierdistancequery.GetTotalTravelDistanceQueryHandler;
import com.ozkan.couriertracking.application.exception.CourierNotFoundException;
import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.repository.CourierRepository;
import com.ozkan.couriertracking.presentation.response.TotalTravelDistanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GetTotalTravelDistanceQueryHandlerTest {
    @Mock
    private CourierRepository courierRepository;

    @InjectMocks
    private GetTotalTravelDistanceQueryHandler handler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void handle_shouldCalculateTotalDistance() {
        // Arrange
        GetTotalTravelDistanceQuery query = new GetTotalTravelDistanceQuery(123L);

        var courier1 = Courier.builder()
                .courierId(123L)
                .longitude(40.0)
                .latitude(29.0)
                .storeName("Store1")
                .timestamp(System.currentTimeMillis())
                .build();

        var courier2 = Courier.builder()
                .courierId(123L)
                .longitude(41.0)
                .latitude(30.0)
                .storeName("Store2")
                .timestamp(System.currentTimeMillis())
                .build();

        List<Courier> courierLogs = List.of(
                courier1, courier2
        );
        when(courierRepository.findByCourierIdOrderByTimestampAsc(123L)).thenReturn(courierLogs);

        // Act
        TotalTravelDistanceResponse totalDistance = handler.handle(query);

        // Assert
        assertEquals("147,41 km", totalDistance.getTotalDistance());
    }

    @Test
    void handle_shouldThrowAnErrorWhenCourierLogIsNotExists() {
        // Arrange
        GetTotalTravelDistanceQuery query = new GetTotalTravelDistanceQuery(123L);

        List<Courier> courierLogs = Collections.emptyList();

        when(courierRepository.findByCourierIdOrderByTimestampAsc(123L)).thenReturn(courierLogs);

        // then
        assertThrows(CourierNotFoundException.class, () -> handler.handle(query));
    }
}
