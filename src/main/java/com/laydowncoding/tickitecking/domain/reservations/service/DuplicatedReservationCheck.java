package com.laydowncoding.tickitecking.domain.reservations.service;

public interface DuplicatedReservationCheck {

    Boolean isDuplicated(String key, String value, Long expiredTime);

    void deleteValue(String key, String value);
}
