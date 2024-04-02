package com.laydowncoding.tickitecking.domain.reservations.repository;

import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertCapacityDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.querydsl.core.Tuple;
import java.util.List;

public interface ReservationRepositoryQuery {

    List<UnreservableSeat> findUnreservableSeats(Long concertId);

    List<Tuple> findLockedSeats(Long concertId);

    List<Tuple> findReservedSeats(Long concertId);

    ConcertCapacityDto findCapacity(Long concertId);
}
