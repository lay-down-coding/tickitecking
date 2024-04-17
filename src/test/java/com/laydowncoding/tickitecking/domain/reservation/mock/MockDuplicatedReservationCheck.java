package com.laydowncoding.tickitecking.domain.reservation.mock;

import com.laydowncoding.tickitecking.domain.reservations.service.DuplicatedReservationCheck;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MockDuplicatedReservationCheck implements DuplicatedReservationCheck {

    private final Map<String, Set<String>> store = new ConcurrentHashMap<>();

    @Override
    public Boolean isDuplicated(String key, String value, Long expiredTime) {
        Set<String> values = store.computeIfAbsent(key, k -> new HashSet<>());
        return !values.add(value);
    }

    @Override
    public void deleteValue(String key, String value) {

    }
}
