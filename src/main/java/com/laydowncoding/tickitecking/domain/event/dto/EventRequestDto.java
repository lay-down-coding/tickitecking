package com.laydowncoding.tickitecking.domain.event.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class EventRequestDto {
  String name;
  String description;
  String catagory;
  LocalDate start_date;
  Long auditorium_id;
  public EventRequestDto(String name,
  String description,
  String catagory,
  LocalDate start_date,
  Long auditorium_id){
    this.name=name;
    this.description=description;
    this.catagory=catagory;
    this.start_date=start_date;
    this.auditorium_id=auditorium_id;
  }
}
