package com.ozkan.couriertracking.infrastructure.repository;

import com.ozkan.couriertracking.domain.model.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findByCourierIdAndStoreNameAndTimestampBetween(
            Long courierId, String storeName, long startTime, long endTime
    );

    List<Courier> findByCourierIdOrderByTimestampAsc(Long courierId);
}
