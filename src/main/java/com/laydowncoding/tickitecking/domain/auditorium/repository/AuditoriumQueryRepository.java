package com.laydowncoding.tickitecking.domain.auditorium.repository;

import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import java.util.List;

public interface AuditoriumQueryRepository {

  List<AuditoriumResponseDto> getAuditoriumAllByCompanyUserId(Long companyUserId);

  List<AuditoriumResponseDto> getAuditoriumAll();

  AuditoriumResponseDto getAuditoriumByAuditoriumId(Long auditoriumId);
}
