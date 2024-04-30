package com.laydowncoding.tickitecking.domain.auditorium.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.INVALID_COMPANY_USER_ID;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.NOT_FOUND_AUDITORIUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatStatus;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuditoriumServiceTest {

    @Mock
    private AuditoriumRepository auditoriumRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private AuditoriumServiceImpl auditoriumService;

    AuditoriumRequestDto requestDto;

    @Mock
    Auditorium auditorium;

    @Mock
    Seat seat;

    @BeforeEach
    void setup() {
        requestDto = new AuditoriumRequestDto(
                "auditorium1",
                "address1",
                "10",
                "C");

        auditorium = new Auditorium(
                "auditorium1",
                "address1",
                "10",
                "C",
                1L
        );

        seat = Seat.builder()
                .auditoriumId(1L)
                .concertId(1L)
                .horizontal("A")
                .vertical("1")
                .grade("G")
                .seatStatus(SeatStatus.AVAILABLE)
                .build();
    }

    @Test
    public void create_auditorium() {
        when(auditoriumRepository.save(any(Auditorium.class))).thenReturn(new Auditorium());

        auditoriumService.createAuditorium(requestDto, 1L);

        verify(auditoriumRepository, times(1)).save(any(Auditorium.class));
    }

    @Test
    public void update_auditorium() {
        given(auditoriumRepository.findById(any(Long.class))).willReturn(Optional.of(auditorium));

        AuditoriumRequestDto requestDto = new AuditoriumRequestDto(
                "update name",
                "update address",
                "10",
                "D");

        auditoriumService.updateAuditorium(requestDto, 1L, 1L);

        verify(auditoriumRepository).findById(1L);
    }

    @Test
    public void testUpdateAuditorium_InvalidAuditoriumId() {
        given(auditoriumRepository.findById(any(Long.class))).willReturn(Optional.empty());

        try {
            auditoriumService.updateAuditorium(requestDto, 1L, 1L);
        } catch (CustomRuntimeException e) {
            assertThat(e).hasMessage(NOT_FOUND_AUDITORIUM.getMessage());
        }
    }

    @Test
    public void testUpdateAuditorium_InvalidUser() {
        given(auditoriumRepository.findById(any(Long.class))).willReturn(Optional.of(auditorium));

        try {
            auditoriumService.updateAuditorium(requestDto, 1L, 567L);
        } catch (CustomRuntimeException e) {
            assertThat(e).hasMessage(INVALID_COMPANY_USER_ID.getMessage());
        }
    }

    @Test
    public void testDeleteAuditorium_Success() {
        given(auditoriumRepository.findById(any(Long.class))).willReturn(Optional.of(auditorium));

        auditoriumService.deleteAuditorium(1L, 1L);

        verify(auditoriumRepository).delete(auditorium);
    }

    @Test
    public void testDeleteAuditorium_InvalidAuditoriumId() {
        given(auditoriumRepository.findById(any(Long.class))).willReturn(Optional.empty());

        try {
            auditoriumService.deleteAuditorium(1L, 1L);
        } catch (CustomRuntimeException e) {
            assertThat(e).hasMessage(NOT_FOUND_AUDITORIUM.getMessage());
        }
    }

    @Test
    public void testDeleteAuditorium_InvalidUser() {
        given(auditoriumRepository.findById(any(Long.class))).willReturn(Optional.of(auditorium));

        try {
            auditoriumService.deleteAuditorium(1L, 1L);
        } catch (CustomRuntimeException e) {
            assertThat(e).hasMessage(INVALID_COMPANY_USER_ID.getMessage());
        }
    }

    @Test
    public void testGetAuditoriums_Success() {
        List<AuditoriumResponseDto> auditoriumList = List.of(new AuditoriumResponseDto(),
                new AuditoriumResponseDto());
        when(auditoriumRepository.getAuditoriumAllByCompanyUserId(any(Long.class))).thenReturn(
                auditoriumList);

        List<AuditoriumResponseDto> result = auditoriumService.getAuditoriums(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetAuditoriums_NoAuditoriumsFound() {
        when(auditoriumRepository.getAuditoriumAllByCompanyUserId(any(Long.class))).thenReturn(
                List.of());

        List<AuditoriumResponseDto> result = auditoriumService.getAuditoriums(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAuditorium_Success() {
        when(auditoriumRepository.getAuditoriumByAuditoriumId(any(Long.class))).thenReturn(
                new AuditoriumResponseDto()
        );

        AuditoriumResponseDto result = auditoriumService.getAuditorium(1L);

        assertNotNull(result);
    }

    @Test
    public void testGetAuditorium_AuditoriumNotFound() {
        when(auditoriumRepository.getAuditoriumByAuditoriumId(any(Long.class))).thenReturn(null);

        AuditoriumResponseDto result = auditoriumService.getAuditorium(1L);

        assertNull(result);
    }
}
