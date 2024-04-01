package com.laydowncoding.tickitecking.domain.reservations.repository;

import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationsRepository extends JpaRepository<Reservation, Long> {

}
