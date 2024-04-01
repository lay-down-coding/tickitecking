package com.laydowncoding.tickitecking.domain.reservations.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ReservationErrorCode.*;

import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    @Override
    public ReservationResponseDto createReservation(Long userId, Long eventId,
        ReservationRequestDto requestDto) {

        Seat seat = findSeat(requestDto.getSeatId());
        checkAvailability(seat.getAvailability());

        Reservation reservation = Reservation.builder()
            .status("Y")
            .userId(userId)
            .eventId(eventId)
            .seatId(requestDto.getSeatId())
            .build();
        Reservation save = reservationRepository.save(reservation);

        return ReservationResponseDto.builder()
            .id(save.getId())
            .status(save.getStatus())
            .userId(save.getUserId())
            .eventId(save.getEventId())
            .seatId(save.getSeatId())
            .build();
    }

    private void checkAvailability(String availability) {
        if (availability.equals("N")) {
            throw new CustomRuntimeException(NOT_AVAILABLE_SEAT.getMessage());
        }
    }

    private Seat findSeat(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_SEAT.getMessage())
        );
    }
}
