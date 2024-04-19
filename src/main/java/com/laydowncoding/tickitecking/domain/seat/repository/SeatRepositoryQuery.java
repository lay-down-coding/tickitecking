package com.laydowncoding.tickitecking.domain.seat.repository;

import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import java.util.List;

public interface SeatRepositoryQuery {

    void saveAllSeat(List<Seat> seatList);

    void updateAllSeat(List<Seat> seatList);
}
