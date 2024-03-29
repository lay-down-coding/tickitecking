package com.laydowncoding.tickitecking.domain.auditorium.service;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;

public interface AuditoriumService {

  void createAuditorium(AuditoriumRequestDto auditoriumRequest);

  void updateAuditorium(AuditoriumRequestDto auditoriumRequest, Long auditoriumId);

  void deleteAuditorium(Long auditoriumId);
}
