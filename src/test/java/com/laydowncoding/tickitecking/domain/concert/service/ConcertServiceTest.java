package com.laydowncoding.tickitecking.domain.concert.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.BDDMockito.*;

import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertCreateRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @DisplayName("콘서트 생성 - 성공")
    @Test
    void create_success() {
        //given
        given(auditoriumRepository.findById(any())).willReturn(Optional.of(new Auditorium()));
        ConcertCreateRequestDto request = ConcertCreateRequestDto.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .auditoriumId(1L)
            .build();

        //when & then
        assertDoesNotThrow(() -> concertService.createConcert(1L, request));
        verify(concertRepository, times(1)).save(any(Concert.class));
    }

    @DisplayName("콘서트 생성 - 실패")
    @Test
    void create_fail() {
        //given
        given(auditoriumRepository.findById(any())).willReturn(Optional.empty());
        ConcertCreateRequestDto request = ConcertCreateRequestDto.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .auditoriumId(1L)
            .build();
        //when & then
        assertThatThrownBy(() -> concertService.createConcert(1L, request))
            .isInstanceOf(CustomRuntimeException.class);
    }

    @DisplayName("콘서트 단건 조회 - 성공")
    @Test
    void get_success() {
        //given
        Concert concert = Concert.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .companyUserId(1L)
            .auditoriumId(1L)
            .build();
        given(concertRepository.findById(any())).willReturn(Optional.of(concert));
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
        Concert concert1 = Concert.builder()
            .name("concertname")
            .description("description")
            .startTime(LocalDateTime.now())
            .companyUserId(1L)
            .auditoriumId(1L)
            .build();

        Concert concert2 = Concert.builder()
            .name("concertname2")
            .description("description2")
            .startTime(LocalDateTime.now())
            .companyUserId(1L)
            .auditoriumId(1L)
            .build();
        given(concertRepository.findAll()).willReturn(List.of(concert1, concert2));

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
}
