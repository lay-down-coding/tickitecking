package com.laydowncoding.tickitecking.domain.auditorium.service;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import java.util.List;

public interface AuditoriumService {

  void createAuditorium(AuditoriumRequestDto auditoriumRequest, Long userId);

  void updateAuditorium(AuditoriumRequestDto auditoriumRequest, Long auditoriumId, Long userId);

  void deleteAuditorium(Long auditoriumId, Long userId);

  List<AuditoriumResponseDto> getAuditoriums();

  AuditoriumResponseDto getAuditorium(Long auditoriumId);
}
