package com.laydowncoding.tickitecking.domain.auditoriums.service;

import com.laydowncoding.tickitecking.domain.auditoriums.respository.AuditoriumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditoriumService {

  private final AuditoriumRepository auditoriumRepository;
}
