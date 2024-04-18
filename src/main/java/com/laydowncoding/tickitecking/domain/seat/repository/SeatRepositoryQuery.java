package com.laydowncoding.tickitecking.domain.seat.repository;

import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import java.util.List;

public interface SeatRepositoryQuery {

    Boolean isReservable(Long eventId, String horizontal, String vertical);

    void saveAllSeat(List<Seat> seatList);

    void updateAllSeat(List<Seat> seatList);
}
