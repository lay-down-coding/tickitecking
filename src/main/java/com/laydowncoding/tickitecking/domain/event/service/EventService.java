package com.laydowncoding.tickitecking.domain.event.service;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.domain.event.dto.EventResponseDto;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import com.laydowncoding.tickitecking.domain.event.repository.EventRepository;
import com.laydowncoding.tickitecking.domain.event.repository.projection.Eventsinfo;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {
private final EventRepository eventRepository;

  public CommonResponse<?> createEvent(Long userid, EventRequestDto dto) {
    Event save = eventRepository.save(new Event(dto,userid));
    return CommonResponse
        .builder()
        .data(save.getId())
        .build();
  }
  public CommonResponse<Object> getEvents(){
    List<Eventsinfo> list = eventRepository.findByStartAtIsAfter(LocalDate.now());
    List <EventResponseDto> ls=new ArrayList<>();
    for (Eventsinfo e : list){
      ls.add(new EventResponseDto(e));
    }
    return CommonResponse
        .builder()
        .data(list)
        .build();
  }
  public CommonResponse<Object> getEventById(Long id){
    Event event = eventRepository.findById(id).orElseThrow(()-> new NoSuchElementException("해당 이벤트를 찾을수 없어요"));
    return CommonResponse
        .builder()
        .data(event)
        .build();
  }
  public CommonResponse<Object> getEventByUser(Long userid){
    List<Eventsinfo> list = eventRepository.findAllByUserid(userid);
    List <EventResponseDto> ls=new ArrayList<>();
    for (Eventsinfo e : list){
      ls.add(new EventResponseDto(e));
    }
    return CommonResponse
        .builder()
        .data(list)
        .build();
  }

  @Transactional
  public CommonResponse<Object> update(Long Userid,Long id, EventRequestDto dto) {
    Event event = eventRepository.findByIdAndUserid(id,Userid);
    event.update(dto);
    return CommonResponse
        .builder()
        .data(event.getId())
        .build();
  }
  @Transactional
  public CommonResponse<?> delete(Long Userid,Long id) {
    Event event = eventRepository.findByIdAndUserid(id,Userid);
    event.setDelete();
    return CommonResponse
        .builder()
        .data(1L)
        .build();
  }
}
