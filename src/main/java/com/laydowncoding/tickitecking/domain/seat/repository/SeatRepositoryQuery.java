package com.laydowncoding.tickitecking.domain.seat.repository;

public interface SeatRepositoryQuery {

    Boolean isReservable(Long eventId, String horizontal, String vertical);
}
