package com.laydowncoding.tickitecking.domain.reservation.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.domain.concert.service.ConcertServiceImpl;
import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertSeatResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.reservations.service.DuplicatedReservationCheckImpl;
import com.laydowncoding.tickitecking.domain.reservations.service.ReservationServiceImpl;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatStatus;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SeatRepository seatRepository;

    @Mock
    ConcertRepository concertRepository;

    @Mock
    DuplicatedReservationCheckImpl duplicatedReservationCheck;

    @Mock
    ConcertServiceImpl concertService;

    Reservation reservation;
    ReservationRequestDto reservationRequestDto;
    UnreservableSeat unreservableSeat1;
    UnreservableSeat unreservableSeat2;
    Seat seat;
    Concert concert;
    ConcertResponseDto concertResponseDto;

    @BeforeEach
    void setup() {
        reservation = Reservation.builder()
            .status("Y")
            .userId(1L)
            .concertId(1L)
            .seatId(1L)
            .build();
        reservationRequestDto = ReservationRequestDto.builder()
            .horizontal("A")
            .vertical("1")
            .build();
        unreservableSeat1 = UnreservableSeat.builder()
            .horizontal("A")
            .vertical("2")
            .status(SeatStatus.RESERVED)
            .build();
        unreservableSeat2 = UnreservableSeat.builder()
            .horizontal("A")
            .vertical("3")
            .status(SeatStatus.LOCKED)
            .build();
        seat = Seat.builder()
            .concertId(1L)
            .horizontal("A")
            .vertical("1")
            .grade("G")
            .auditoriumId(1L)
            .seatStatus(SeatStatus.AVAILABLE)
            .build();
        concert = Concert.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .companyUserId(1L)
            .auditoriumId(1L)
            .build();
        concertResponseDto = ConcertResponseDto.builder().build();
    }

    @DisplayName("예매 생성 - 성공")
    @Test
    void reservations_create_success() {
        //given
        given(seatRepository.findSeatForReservation(any(), any(), any()))
            .willReturn(seat);
        given(reservationRepository.save(any(Reservation.class))).willReturn(reservation);
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));
        given(duplicatedReservationCheck.isDuplicated(any(), any(), any())).willReturn(false);

        //when
        ReservationResponseDto responseDto = reservationService.createReservation(1L,
            1L, reservationRequestDto);

        //then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getStatus()).isEqualTo("Y");
    }

    @DisplayName("예매 생성 - 실패 예약 불가능한 좌석")
    @Test
    void reservation_create_fail() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));
        given(duplicatedReservationCheck.isDuplicated(any(), any(), any())).willReturn(true);

        //when & then
        assertThatThrownBy(() ->
            reservationService.createReservation(1L, 1L, reservationRequestDto))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("콘서트 좌석 조회- 성공")
    @Test
    void concert_seats_get_success() {
        //given
        given(reservationRepository.findUnreservableSeats(any()))
            .willReturn(List.of(unreservableSeat1, unreservableSeat2));
        given(concertService.getConcert(anyLong())).willReturn(concertResponseDto);
        given(concertRepository.existsById(any())).willReturn(true);

        //when
        ConcertSeatResponseDto response = reservationService.getConcertSeats(1L);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getUnreservableSeats()).hasSize(2);
    }

    @DisplayName("콘서트 좌석 조회- 실패 없는 콘서트 id")
    @Test
    void concert_seats_get_fail() {
        //given
        given(concertRepository.existsById(any())).willReturn(false);

        //when & then
        assertThatThrownBy(() ->
            reservationService.getConcertSeats(1L))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("예매 삭제 - 성공")
    @Test
    void delete_reservation_success() {
        //given
        given(reservationRepository.findById(any())).willReturn(Optional.of(reservation));
        given(seatRepository.findById(anyLong())).willReturn(Optional.of(seat));


        //when & then
        assertDoesNotThrow(() -> reservationService.deleteReservation(1L, 1L));
        verify(reservationRepository, times(1)).delete(any(Reservation.class));
    }

    @DisplayName("예매 삭제 - 실패 없는 예매 id")
    @Test
    void delete_reservation_fail() {
        //given
        given(reservationRepository.findById(any())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> reservationService.deleteReservation(1L, 1L));
    }
}
