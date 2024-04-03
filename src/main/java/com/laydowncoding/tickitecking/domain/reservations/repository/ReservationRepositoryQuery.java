package com.laydowncoding.tickitecking.domain.reservations.repository;

import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertInfoDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.laydowncoding.tickitecking.domain.user.dto.UserReservationResponseDto;
import com.querydsl.core.Tuple;
import java.util.List;

public interface ReservationRepositoryQuery {

    List<UnreservableSeat> findUnreservableSeats(Long concertId);

    List<Tuple> findLockedSeats(Long concertId);

    List<Tuple> findReservedSeats(Long concertId);

    ConcertInfoDto findConcertInfo(Long concertId);

    List<UserReservationResponseDto> findReservations(Long userId);

    List<AdminReservationResponseDto> getReservationAll();
}
