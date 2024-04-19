package com.laydowncoding.tickitecking.domain.seat.dto.response;

import com.laydowncoding.tickitecking.domain.seat.entity.SeatStatus;
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
  private SeatStatus status;
  private String grade;
}
