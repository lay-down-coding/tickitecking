package com.laydowncoding.tickitecking.domain.event.controller;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.domain.event.dto.EventResponseDto;
import com.laydowncoding.tickitecking.domain.event.repository.projection.EventsInfoWithOutUserId;
import com.laydowncoding.tickitecking.domain.event.service.EventService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import jakarta.validation.constraints.Null;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
  private final EventService eventService;

  @PostMapping
  public ResponseEntity<CommonResponse<Long>> createEvent(Long userId,@RequestBody EventRequestDto dto){
    // 유저 jwt 구현 되기 전까지 임시로 만들어 놨습니다.
    userId=1L;
    return CommonResponse
        .ok(eventService.createEvent(userId,dto));
  }
  @GetMapping
  public ResponseEntity<CommonResponse<List<EventsInfoWithOutUserId>>> getUpcomingEvents(){
    return CommonResponse
        .ok(eventService.getEvents());
  }
  @GetMapping("/{userId}")
  public ResponseEntity<CommonResponse<List<EventsInfoWithOutUserId>>> getSelectedEvents(@PathVariable Long userId){
    return CommonResponse.ok(eventService.getEventByUser(userId));
  }
  @GetMapping("/my-events")
  public ResponseEntity<CommonResponse<List<EventsInfoWithOutUserId>>> getEvents(Long userId){
    // 유저 jwt 구현 되기 전까지 임시로 만들어 놨습니다.
    userId = 1L;
    return CommonResponse
        .ok(eventService.getEventByUser(userId));
  }
  @PutMapping("/{eventId}")
  public ResponseEntity<CommonResponse<Void>> updateEvent(Long userId,@PathVariable Long eventId, @RequestBody EventRequestDto dto){
    eventService.update(userId,eventId,dto);
    return CommonResponse.ok(null);
  }
  @DeleteMapping("/{eventId}")
  public ResponseEntity<CommonResponse<Void>> deleteEvent(Long userId,@PathVariable Long eventId) {
    // 유저 jwt 구현 되기 전까지 임시로 만들어 놨습니다.
    userId = 1l;
    eventService.delete(userId, eventId);
    return CommonResponse.ok(null);
  }
}

