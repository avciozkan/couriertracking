package com.ozkan.couriertracking.handler;

import com.ozkan.couriertracking.application.exception.DuplicateStoreEntryException;
import com.ozkan.couriertracking.application.exception.OutOfStoreRadiusException;
import com.ozkan.couriertracking.application.service.Store;
import com.ozkan.couriertracking.application.service.StoreService;
import com.ozkan.couriertracking.application.updatecourierlocationcommand.UpdateCourierLocationCommand;
import com.ozkan.couriertracking.application.updatecourierlocationcommand.UpdateCourierLocationCommandHandler;
import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.repository.CourierRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UpdateCourierLocationCommandHandlerTest {

    @Mock
    private CourierRepository courierRepository;

    @Mock
    private StoreService storeService;

    @InjectMocks
    private UpdateCourierLocationCommandHandler handler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handle_shouldSaveCourierLog_whenAllConditionsAreMet() {
        // given
        UpdateCourierLocationCommand command = UpdateCourierLocationCommand.builder()
                .courierId(1L)
                .latitude(40.1235)
                .longitude(29.1235)
                .timestamp(System.currentTimeMillis())
                .build();

        Store store = new Store("Migros Store", 40.1235, 29.1235);
        List<Store> stores = List.of(store);

        when(storeService.getAllStores()).thenReturn(stores);
        when(courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(
                anyLong(),
                anyString(),
                anyLong(),
                anyLong()
        )).thenReturn(Optional.empty());

        // when
        assertDoesNotThrow(() -> handler.handle(command));

        // then
        verify(storeService, times(1)).getAllStores();
        verify(courierRepository, times(1)).save(any(Courier.class));
    }

    @Test
    void handle_shouldThrowOutOfStoreRadiusException_whenNoNearbyStores() {
        // Arrange
        UpdateCourierLocationCommand command = UpdateCourierLocationCommand.builder()
                .courierId(1L)
                .latitude(40.1235)
                .longitude(29.1235)
                .timestamp(System.currentTimeMillis())
                .build();

        List<Store> stores = List.of(
                new Store("Far Store", 41.0, 30.0)
        );

        when(storeService.getAllStores()).thenReturn(stores);

        // Act & Assert
        assertThrows(OutOfStoreRadiusException.class, () -> handler.handle(command));

        verify(storeService, times(1)).getAllStores();
        verifyNoInteractions(courierRepository);
    }

    @Test
    void handle_shouldThrowDuplicateStoreEntryException_whenDuplicateEntryExists() {
        // Arrange
        UpdateCourierLocationCommand command = UpdateCourierLocationCommand.builder()
                .courierId(1L)
                .latitude(40.1235)
                .longitude(29.1235)
                .timestamp(System.currentTimeMillis())
                .build();

        Store store = new Store("Migros Store", 40.1235, 29.1235);
        List<Store> stores = List.of(store);

        when(storeService.getAllStores()).thenReturn(stores);
        when(courierRepository.findByCourierIdAndStoreNameAndTimestampBetween(
                eq(command.getCourierId()),
                eq(store.getName()),
                anyLong(),
                anyLong()
        )).thenReturn(Optional.of(new Courier()));

        // Act & Assert
        assertThrows(DuplicateStoreEntryException.class, () -> handler.handle(command));

        verify(storeService, times(1)).getAllStores();
        verify(courierRepository, times(1)).findByCourierIdAndStoreNameAndTimestampBetween(
                eq(command.getCourierId()),
                eq(store.getName()),
                anyLong(),
                anyLong()
        );
        verifyNoMoreInteractions(courierRepository);
    }
}
