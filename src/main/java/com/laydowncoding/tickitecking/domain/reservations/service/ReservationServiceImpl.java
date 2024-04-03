package com.laydowncoding.tickitecking.domain.reservations.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ReservationErrorCode.*;

import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertInfoDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertSeatResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;
    private final ConcertRepository concertRepository;

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
        validateConcertId(concertId);
        List<UnreservableSeat> unreservableSeats = reservationRepository.findUnreservableSeats(
            concertId);
        ConcertInfoDto concertInfoDto = reservationRepository.findConcertInfo(concertId);
        return ConcertSeatResponseDto.builder()
            .concertInfoDto(concertInfoDto)
            .unreservableSeats(unreservableSeats)
            .build();
    }

    @Override
    public void deleteReservation(Long userId, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        validateUserId(reservation.getUserId(), userId);
        reservationRepository.delete(reservation);
    }

    private boolean isReservable(Long eventId, String horizontal, String vertical) {
        return seatRepository.isReservable(eventId, horizontal, vertical);
    }

    private Reservation findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_RESERVATION.getMessage())
        );
    }

    private void validateUserId(Long origin, Long input) {
        if (!Objects.equals(origin, input)) {
            throw new CustomRuntimeException(INVALID_USER_ID.getMessage());
        }
    }

    private void validateConcertId(Long concertId) {
        if (!concertRepository.existsById(concertId)) {
            throw new CustomRuntimeException(ConcertErrorCode.NOT_FOUND_CONCERT.getMessage());
        }
    }
}
