package com.laydowncoding.tickitecking.domain.event.controller;

import com.laydowncoding.tickitecking.domain.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {
  private final EventService eventService;
}

