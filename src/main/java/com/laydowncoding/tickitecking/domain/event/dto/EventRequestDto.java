package com.laydowncoding.tickitecking.domain.event.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class EventRequestDto {
  String name;
  String description;
  String catagory;
  LocalDate start_date;
  Long auditorium_id;
}
