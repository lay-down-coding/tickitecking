package com.laydowncoding.tickitecking.domain.reservations.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ReservationErrorCode.INVALID_USER_ID;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ReservationErrorCode.NOT_FOUND_RESERVATION;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.domain.concert.service.ConcertService;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertSeatResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode;
import com.laydowncoding.tickitecking.global.exception.errorcode.SeatErrorCode;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private final DuplicatedReservationCheck duplicatedReservationCheck;
    private final ConcertService concertService;

    @Override
    public ReservationResponseDto createReservation(Long userId, Long concertId,
        ReservationRequestDto requestDto) {
        Concert concert = findConcert(concertId);
        if (isTaken(concertId, requestDto.getHorizontal(), requestDto.getVertical(),
            Duration.between(LocalDateTime.now(), concert.getStartTime()).toSeconds())) {
            throw new CustomRuntimeException("redis가 막았습니다.");
        }

        Seat seat = seatRepository.findSeatForReservation(concertId, requestDto.getHorizontal(),
            requestDto.getVertical());
        if (!seat.isReservable()) {
            throw new CustomRuntimeException("예약 불가능한 좌석입니다.");
        }
        seat.reserve();

        Reservation reservation = Reservation.builder()
            .status("Y")
            .userId(userId)
            .concertId(concertId)
            .seatId(seat.getId())
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
    @Transactional(readOnly = true)
    public ConcertSeatResponseDto getConcertSeats(Long concertId) {
        validateConcertId(concertId);

        List<UnreservableSeat> unreservableSeats = reservationRepository.findUnreservableSeats(
            concertId);
        ConcertResponseDto concert = concertService.getConcert(concertId);

        return ConcertSeatResponseDto.builder()
            .concertResponseDto(concert)
            .unreservableSeats(unreservableSeats)
            .build();
    }

    @Override
    public void deleteReservation(Long userId, Long reservationId) {
        Reservation reservation = findReservation(reservationId);
        validateUserId(reservation.getUserId(), userId);

        Seat seat = findSeat(reservation.getSeatId());

        duplicatedReservationCheck.deleteValue(String.valueOf(reservation.getConcertId()),
            seat.getHorizontal() + seat.getVertical());
        seat.cancel();
        reservationRepository.delete(reservation);
    }

    private Boolean isTaken(Long concertId, String horizontal, String vertical, Long expiredTime) {
        String key = String.valueOf(concertId);
        String value = horizontal + vertical;
        return duplicatedReservationCheck.isDuplicated(key, value, expiredTime);
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

    private Concert findConcert(Long concertId) {
        return concertRepository.findById(concertId).orElseThrow(
            () -> new CustomRuntimeException(ConcertErrorCode.NOT_FOUND_CONCERT.getMessage())
        );
    }

    private Seat findSeat(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(
            () -> new CustomRuntimeException(SeatErrorCode.NOT_FOUND_SEAT.getMessage())
        );
    }
}
