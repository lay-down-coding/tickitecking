package com.laydowncoding.tickitecking.domain.concert.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.domain.seat.dto.AuditoriumCapacityDto;
import com.laydowncoding.tickitecking.domain.seat.dto.SeatPriceDto;
import com.laydowncoding.tickitecking.domain.seat.service.SeatServiceImpl;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.time.LocalDateTime;
import java.util.Collections;
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
public class ConcertServiceTest {

    @InjectMocks
    ConcertServiceImpl concertService;

    @Mock
    ConcertRepository concertRepository;

    @Mock
    AuditoriumRepository auditoriumRepository;

    @Mock
    SeatServiceImpl seatService;

    Concert concert;
    SeatPriceDto seatPriceDto;
    Auditorium auditorium;
    ConcertRequestDto concertRequestDto;
    @BeforeEach
    void setup() {
        concert = Concert.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .companyUserId(1L)
            .auditoriumId(1L)
            .build();
        seatPriceDto = SeatPriceDto.builder()
            .goldPrice(10000.0)
            .silverPrice(5000.0)
            .bronzePrice(1000.0)
            .build();
        auditorium = Auditorium.builder()
            .name("auditoriumName")
            .address("강남구")
            .maxRow("Z")
            .maxColumn("10")
            .companyUserId(1L)
            .build();
        concertRequestDto = ConcertRequestDto.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .auditoriumId(1L)
            .goldPrice(10000.0)
            .silverPrice(5000.0)
            .bronzePrice(1000.0)
            .build();
    }

    @DisplayName("콘서트 생성 - 성공")
    @Test
    void create_success() {
        //given
        given(auditoriumRepository.findById(any())).willReturn(Optional.of(auditorium));
        given(concertRepository.save(any(Concert.class))).willReturn(concert);

        //when & then
        assertDoesNotThrow(() -> concertService.createConcert(1L, concertRequestDto));
        verify(concertRepository, times(1)).save(any(Concert.class));
        verify(seatService, times(1)).createSeatPrices(any(), any(SeatPriceDto.class));
    }

    @DisplayName("콘서트 생성 - 실패")
    @Test
    void create_fail() {
        //given
        given(auditoriumRepository.findById(any())).willReturn(Optional.empty());
        //when & then
        assertThatThrownBy(() -> concertService.createConcert(1L, concertRequestDto))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("콘서트 단건 조회 - 성공")
    @Test
    void get_success() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));
        given(seatService.getSeatPrices(any())).willReturn(seatPriceDto);
        given(auditoriumRepository.findById(any())).willReturn(Optional.of(auditorium));

        //when
        ConcertResponseDto response = concertService.getConcert(1L);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("concertname");
    }

    @DisplayName("콘서트 단건 조회 - 실패")
    @Test
    void get_fail() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> concertService.getConcert(1L))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("콘서트 전체 조회 - 성공")
    @Test
    void getAll_success() {
        //given
        Concert concert2 = Concert.builder()
            .name("concertname2")
            .description("description2")
            .startTime(LocalDateTime.now())
            .companyUserId(1L)
            .auditoriumId(1L)
            .build();
        given(concertRepository.findAll()).willReturn(List.of(concert, concert2));
        given(seatService.getSeatPrices(any())).willReturn(seatPriceDto);
        given(auditoriumRepository.findById(any())).willReturn(Optional.of(auditorium));

        //when
        List<ConcertResponseDto> response = concertService.getAllConcerts();

        //then
        assertThat(response).hasSize(2);
    }

    @DisplayName("콘서트 전체 조회 - 실패")
    @Test
    void getAll_fail() {
        //given
        given(concertRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<ConcertResponseDto> response = concertService.getAllConcerts();

        //then
        assertThat(response).hasSize(0);
    }

    @DisplayName("콘서트 수정 - 성공")
    @Test
    void update_success() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));
        given(seatService.updateSeatPrices(any(), any())).willReturn(seatPriceDto);
        given(auditoriumRepository.findById(anyLong())).willReturn(Optional.of(auditorium));
        ConcertRequestDto requestDto = ConcertRequestDto.builder()
            .name("updateName")
            .description("updateDescription")
            .startTime(LocalDateTime.now())
            .auditoriumId(1L)
            .build();
        //when
        ConcertResponseDto response = concertService.updateConcert(1L, 1L, requestDto);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("updateName");
        verify(seatService, times(1)).updateSeats(any(), anyLong(),
            any(AuditoriumCapacityDto.class));
    }

    @DisplayName("콘서트 수정 - 실패 회사유저 id가 다름")
    @Test
    void update_fail() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));

        ConcertRequestDto requestDto = ConcertRequestDto.builder()
            .name("updateName")
            .description("updateDescription")
            .startTime(LocalDateTime.now())
            .auditoriumId(1L)
            .build();
        //when & then
        assertThatThrownBy(() ->
            concertService.updateConcert(2L, 1L, requestDto))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("콘서트 삭제 - 성공")
    @Test
    void delete_success() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));

        //when & then
        assertDoesNotThrow(() -> concertService.deleteConcert(1L, 1L));
        verify(concertRepository, times(1)).delete(any(Concert.class));
    }

    @DisplayName("콘서트 삭제 - 실패 회사유저 id가 다름")
    @Test
    void delete_fail() {
        //given
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));

        //when & then
        assertThatThrownBy(() -> concertService.deleteConcert(2L, 1L))
            .isInstanceOf(CustomRuntimeException.class);
    }
}
