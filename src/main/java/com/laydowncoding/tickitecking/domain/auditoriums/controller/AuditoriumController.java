package com.laydowncoding.tickitecking.domain.auditoriums.controller;

import com.laydowncoding.tickitecking.domain.auditoriums.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditoriumController {

  private final AuditoriumService auditoriumService;
}
