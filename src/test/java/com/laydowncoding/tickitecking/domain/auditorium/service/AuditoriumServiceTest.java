package com.laydowncoding.tickitecking.domain.auditorium.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuditoriumServiceTest {

  @Mock
  private AuditoriumRepository auditoriumRepository;

  @InjectMocks
  private AuditoriumServiceImpl auditoriumService;

  @Test
  public void create_auditorium() {
    AuditoriumRequestDto requestDto = new AuditoriumRequestDto(
        "auditorium1",
        "address1",
        "C",
        "30"
    );

    when(auditoriumRepository.save(any(Auditorium.class))).thenReturn(new Auditorium());

    auditoriumService.createAuditorium(
        requestDto.getName(),
        requestDto.getAddress(),
        requestDto.getMaxColumn(),
        requestDto.getMaxRow()
    );

    verify(auditoriumRepository, times(1)).save(any(Auditorium.class));
  }
}
