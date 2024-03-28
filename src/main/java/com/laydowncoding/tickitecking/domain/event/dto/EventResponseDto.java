package com.laydowncoding.tickitecking.domain.event.dto;

import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class EventResponseDto {
  private String name;
  private String description;
  private String catagory;
  private LocalDate start_date;
  private Long auditorium_id;

  public EventResponseDto(Event save) {
    this.name = save.getName();
    description = save.getDescription();
    catagory = save.getCatagory();
    start_date = save.getStartAt();
    auditorium_id = save.getAuditorium_id();
  }
}
