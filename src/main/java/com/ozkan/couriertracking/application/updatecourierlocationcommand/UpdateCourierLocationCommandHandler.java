package com.ozkan.couriertracking.application.updatecourierlocationcommand;

import com.ozkan.couriertracking.application.exception.DuplicateStoreEntryException;
import com.ozkan.couriertracking.application.exception.OutOfStoreRadiusException;
import com.ozkan.couriertracking.application.service.DistanceCalculator;
import com.ozkan.couriertracking.application.service.Store;
import com.ozkan.couriertracking.application.service.StoreService;
import com.ozkan.couriertracking.domain.model.Courier;
import com.ozkan.couriertracking.infrastructure.mediator.base.RequestHandler;
import com.ozkan.couriertracking.infrastructure.repository.CourierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("UpdateCourierLocationCommandHandler")
public class UpdateCourierLocationCommandHandler implements RequestHandler<UpdateCourierLocationCommand, Void> {

    private final int ONU_MINUTE_MILLIS = 60000;
    private final int ONE_HUNDRED_METER = 100;

    private final CourierRepository courierRepository;
    private final StoreService storeService;

    public UpdateCourierLocationCommandHandler(CourierRepository courierRepository, StoreService storeService) {
        this.courierRepository = courierRepository;
        this.storeService = storeService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Void handle(UpdateCourierLocationCommand command) {
        List<Store> stores = storeService.getAllStores();
        Store nearestStore = findNearestStore(command, stores);

        if (nearestStore == null) {
            throw new OutOfStoreRadiusException("Courier is not within 100 meters of any store.");
        }

        boolean hasRecentEntry = hasRecentEntryForStore(command.getCourierId(), nearestStore, command.getTimestamp());

        if (hasRecentEntry) {
            throw new DuplicateStoreEntryException("Courier already logged an entry for this store within the last minute.");
        }

        var courierLog = createCourierLog(command, nearestStore);

        courierRepository.save(courierLog);

        return null;
    }

    private Courier createCourierLog(UpdateCourierLocationCommand command, Store nearestStore) {
        return Courier.builder()
                .courierId(command.getCourierId())
                .storeName(nearestStore.getName())
                .longitude(command.getLongitude())
                .latitude(command.getLatitude())
                .timestamp(command.getTimestamp())
                .build();
    }

    private boolean hasRecentEntryForStore(Long courierId, Store nearestStore, long timestamp) {
        long oneMinuteAgo = timestamp - ONU_MINUTE_MILLIS;
        return courierRepository
                .findByCourierIdAndStoreNameAndTimestampBetween(
                        courierId,
                        nearestStore.getName(),
                        oneMinuteAgo,
                        timestamp
                )
                .isPresent();
    }

    private Store findNearestStore(UpdateCourierLocationCommand command, List<Store> stores) {
        Store nearestStore = null;
        for (Store store : stores) {
            double distance = DistanceCalculator.calculateDistance(
                    command.getLatitude(), command.getLongitude(),
                    store.getLatitude(), store.getLongitude()
            );
            if (distance <= ONE_HUNDRED_METER) {
                nearestStore = store;
                break;
            }
        }
        return nearestStore;
    }
}
