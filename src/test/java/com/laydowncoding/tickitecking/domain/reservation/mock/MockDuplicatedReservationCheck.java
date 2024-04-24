package com.laydowncoding.tickitecking.domain.reservation.mock;

import com.laydowncoding.tickitecking.domain.reservations.service.DuplicatedReservationCheck;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MockDuplicatedReservationCheck implements DuplicatedReservationCheck {

    private final Map<String, Set<String>> store = new ConcurrentHashMap<>();

    @Override
    public synchronized Boolean isDuplicated(String key, String value, Long expiredTime) {
        store.putIfAbsent(key, new HashSet<>());
        return !store.get(key).add(value);
    }

    @Override
    public void deleteValue(String key, String value) {

    }
}
