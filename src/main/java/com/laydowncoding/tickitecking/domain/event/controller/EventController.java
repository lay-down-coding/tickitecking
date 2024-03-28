package com.laydowncoding.tickitecking.domain.event.controller;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.domain.event.service.EventService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
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
  public ResponseEntity<CommonResponse<?>> createEvent(@RequestBody EventRequestDto dto){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(eventService.createEvent(1L,dto))
        ;
  }

  @GetMapping
  public ResponseEntity<CommonResponse<?>> getEvents(){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(eventService.getEvents())
        ;
  }

  //
  @PutMapping("/{id}")
  public ResponseEntity<CommonResponse<?>> updateEvent(@PathVariable Long id, @RequestBody EventRequestDto dto){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(eventService.update(id,dto))
        ;
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonResponse<?>> deleteEvent(Long userid,@PathVariable Long id){
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(eventService.delete())
        ;
  }
}

