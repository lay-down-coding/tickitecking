package com.laydowncoding.tickitecking.domain.auditorium.controller;

import com.laydowncoding.tickitecking.domain.auditorium.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuditoriumController {

  private final AuditoriumService auditoriumService;
}
