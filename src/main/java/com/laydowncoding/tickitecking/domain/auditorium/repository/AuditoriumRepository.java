package com.laydowncoding.tickitecking.domain.auditorium.repository;

import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

  List<Auditorium> findAllByCompanyUserId(Long companyUserId);
}
