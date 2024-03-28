package com.laydowncoding.tickitecking.domain.auditorium.service;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService{

  private final AuditoriumRepository auditoriumRepository;

  @Override
  public void createAuditorium(AuditoriumRequestDto auditoriumRequest) {
    // 유저 아이디 임시
    Auditorium auditorium = new Auditorium(
        auditoriumRequest.getName(),
        auditoriumRequest.getAddress(),
        auditoriumRequest.getMaxColumn(),
        auditoriumRequest.getMaxRow(),
        1L
    );

    auditoriumRepository.save(auditorium);
  }
}
