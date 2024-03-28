package com.laydowncoding.tickitecking.domain.event.service;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.domain.event.dto.EventResponseDto;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import com.laydowncoding.tickitecking.domain.event.repository.EventRepository;
import com.laydowncoding.tickitecking.domain.event.repository.projection.eventsinfo;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
private final EventRepository eventRepository;

  public CommonResponse<?> createEvent(Long userid, EventRequestDto dto) {
    Event save = eventRepository.save(new Event(dto,userid));
    return CommonResponse
        .builder()
        .data(new EventResponseDto(save))
        .build();
  }
  public CommonResponse<Object> getEvents(){
    List<Event> list = eventRepository.findByStartAtIsAfter(LocalDate.now());
    List <EventResponseDto> ls=new ArrayList<>();
    for (Event e : list){
      ls.add(new EventResponseDto(e));
    }
    return CommonResponse
        .builder()
        .data(list)
        .build();
  }

  public CommonResponse<?> update(Long id, EventRequestDto dto) {
    return null;
  }
  public CommonResponse<?> delete() {
    return null;
  }
}
