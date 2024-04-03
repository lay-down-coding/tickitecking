package com.laydowncoding.tickitecking.domain.reservation.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationRepository;
import com.laydowncoding.tickitecking.domain.reservations.service.ReservationService;
import com.laydowncoding.tickitecking.domain.reservations.service.ReservationServiceImpl;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Git;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    ReservationServiceImpl reservationService;

    @Mock
    ReservationRepository reservationRepository;

    @Mock
    SeatRepository seatRepository;

    Reservation reservation;
    ReservationRequestDto reservationRequestDto;

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
    }

    @DisplayName("예매 생성 - 성공")
    @Test
    void reservations_create_success() {
        //given
        given(seatRepository.isReservable(any(), any(), any())).willReturn(true);
        given(seatRepository.findSeatId(any(), any(), any())).willReturn(1L);
        given(reservationRepository.save(any(Reservation.class))).willReturn(reservation);

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
        given(seatRepository.isReservable(any(), any(), any())).willReturn(false);

        //when & then
        assertThatThrownBy(() ->
            reservationService.createReservation(1L, 1L, reservationRequestDto))
            .isInstanceOf(CustomRuntimeException.class);
    }
}
