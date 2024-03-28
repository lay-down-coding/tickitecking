package com.laydowncoding.tickitecking.domain.event.service;

import com.laydowncoding.tickitecking.domain.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
private final EventRepository eventRepository;
}
