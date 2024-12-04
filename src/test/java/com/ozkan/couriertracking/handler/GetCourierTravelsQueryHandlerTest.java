package com.ozkan.couriertracking.handler;

import com.ozkan.couriertracking.application.couriertravelsquery.GetCourierTravelsQuery;
import com.ozkan.couriertracking.application.couriertravelsquery.GetCourierTravelsQueryHandler;
import com.ozkan.couriertracking.application.exception.CourierNotFoundException;
import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.repository.CourierRepository;
import com.ozkan.couriertracking.presentation.response.CourierTravelsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GetCourierTravelsQueryHandlerTest {
    @Mock
    private CourierRepository courierRepository;

    @InjectMocks
    private GetCourierTravelsQueryHandler handler;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_shouldReturnCourierTravelResponses_whenLogsExist() {
        // Arrange
        Long courierId = 123L;
        GetCourierTravelsQuery query = new GetCourierTravelsQuery(courierId);

        List<Courier> mockLogs = Arrays.asList(
                new Courier(1L, courierId, "Store1", 40.1234, 29.1234, System.currentTimeMillis()),
                new Courier(2L, courierId, "Store2", 41.1234, 30.1234, System.currentTimeMillis() + 1000)
        );

        when(courierRepository.findByCourierIdOrderByTimestampAsc(courierId)).thenReturn(mockLogs);

        // Act
        List<CourierTravelsResponse> responses = handler.handle(query);

        // Assert
        assertEquals(2, responses.size());
        assertEquals("Store1", responses.getFirst().getStoreName());
        assertEquals(40.1234, responses.getFirst().getLatitude());
        assertEquals(29.1234, responses.getFirst().getLongitude());

        verify(courierRepository, times(1)).findByCourierIdOrderByTimestampAsc(courierId);
    }

    @Test
    void handle_shouldThrowException_whenNoLogsExist() {
        // Arrange
        Long courierId = 123L;
        GetCourierTravelsQuery query = new GetCourierTravelsQuery(courierId);

        when(courierRepository.findByCourierIdOrderByTimestampAsc(courierId)).thenReturn(List.of());

        // Act & Assert
        assertThrows(CourierNotFoundException.class, () -> handler.handle(query));
        verify(courierRepository, times(1)).findByCourierIdOrderByTimestampAsc(courierId);
    }
}
