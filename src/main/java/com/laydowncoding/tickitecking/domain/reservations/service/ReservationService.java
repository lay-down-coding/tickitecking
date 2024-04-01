package com.laydowncoding.tickitecking.domain.reservations.service;

import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;

public interface ReservationService {

    ReservationResponseDto createReservation(Long userId, Long concertId, ReservationRequestDto requestDto);
}
