package com.laydowncoding.tickitecking.domain.reservation.config;

import com.laydowncoding.tickitecking.domain.reservation.mock.MockDuplicatedReservationCheck;
import com.laydowncoding.tickitecking.domain.reservations.service.DuplicatedReservationCheck;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public abstract class MockTestConfiguration {

    @Bean
    public DuplicatedReservationCheck duplicatedReservationCheck() {
        return new MockDuplicatedReservationCheck();
    }
}
