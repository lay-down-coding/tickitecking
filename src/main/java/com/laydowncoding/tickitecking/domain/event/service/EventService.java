package com.laydowncoding.tickitecking.domain.event.service;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.domain.event.dto.EventResponseDto;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import com.laydowncoding.tickitecking.domain.event.repository.EventRepository;
import com.laydowncoding.tickitecking.domain.event.repository.projection.EventsInfoWithOutUserId;
import com.laydowncoding.tickitecking.global.exception.NoSuchEventException;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventService {
private final EventRepository eventRepository;
// 생성
  public Long createEvent(Long userid, EventRequestDto eventRequestDto) {
    Event save = eventRepository.save(new Event(eventRequestDto,userid));
    //CommonResponse commonResponse = new CommonResponse();
    return save.getId();
  }
  public List<EventsInfoWithOutUserId> getEvents(){
    return eventRepository.findByStartAtIsAfter(LocalDate.now());
  }
  // 생성
  public EventResponseDto getEventById(Long eventId) {
    Event event = eventRepository.findById(eventId)
        .orElseThrow(() -> new NoSuchEventException("해당 이벤트를 찾을수 없어요"));
    return new EventResponseDto(event);
  }
  public List<EventsInfoWithOutUserId> getEventByUser(Long userId){
    List<EventsInfoWithOutUserId> list = eventRepository.findAllByUserid(userId);
    return list;
  }
  @Transactional
  public void update(Long userId,Long eventId, EventRequestDto eventRequestDto) {
    Event event = eventRepository.findById(eventId).orElseThrow();
    event.update(eventRequestDto);
  }
  @Transactional
  public void delete(Long Userid,Long eventId) {
    Event event = eventRepository.findByIdAndUserid(eventId,Userid);
    event.setDelete();
  }
}
