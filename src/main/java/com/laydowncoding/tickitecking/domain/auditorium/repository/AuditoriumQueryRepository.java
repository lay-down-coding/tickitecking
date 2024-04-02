package com.laydowncoding.tickitecking.domain.auditorium.repository;

import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import java.util.List;

public interface AuditoriumQueryRepository {

  List<AuditoriumResponseDto> findAllByCompanyUserId(Long companyUserId);

  List<AuditoriumResponseDto> findAll();

  AuditoriumResponseDto findByAuditoriumId(Long auditoriumId);
}
