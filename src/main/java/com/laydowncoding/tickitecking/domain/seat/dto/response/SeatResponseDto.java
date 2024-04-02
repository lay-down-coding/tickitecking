package com.laydowncoding.tickitecking.domain.seat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponseDto {

  private Long id;
  private String vertical;
  private String horizontal;
  private String availability;
  private String grade;
}
