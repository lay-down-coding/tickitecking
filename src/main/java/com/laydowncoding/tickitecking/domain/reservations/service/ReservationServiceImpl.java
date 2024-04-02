package com.laydowncoding.tickitecking.domain.reservations.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ReservationErrorCode.*;

import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertCapacityDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertSeatResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.List;
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
    public ReservationResponseDto createReservation(Long userId, Long concertId,
        ReservationRequestDto requestDto) {

        if (!isReservable(concertId, requestDto.getHorizontal(), requestDto.getVertical())) {
            throw new CustomRuntimeException("예약 불가능한 좌석입니다.");
        }
        Long seatId = seatRepository.findSeatId(concertId, requestDto.getHorizontal(),
            requestDto.getVertical());

        Reservation reservation = Reservation.builder()
            .status("Y")
            .userId(userId)
            .concertId(concertId)
            .seatId(seatId)
            .build();
        Reservation save = reservationRepository.save(reservation);

        return ReservationResponseDto.builder()
            .id(save.getId())
            .status(save.getStatus())
            .userId(save.getUserId())
            .concertId(save.getConcertId())
            .seatId(save.getSeatId())
            .build();
    }

    @Override
    public ConcertSeatResponseDto getConcertSeats(Long concertId) {
        List<UnreservableSeat> unreservableSeats = reservationRepository.findUnreservableSeats(
            concertId);
        ConcertCapacityDto capacity = reservationRepository.findCapacity(concertId);
        return ConcertSeatResponseDto.builder()
            .concertId(concertId)
            .maxColumn(capacity.getMaxColumn())
            .maxRow(capacity.getMaxRow())
            .unreservableSeats(unreservableSeats)
            .build();
    }

    private boolean isReservable(Long eventId, String horizontal, String vertical) {
        return seatRepository.isReservable(eventId, horizontal, vertical);
    }

    private Seat findSeat(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_SEAT.getMessage())
        );
    }
}
