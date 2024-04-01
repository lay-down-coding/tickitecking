package com.laydowncoding.tickitecking.domain.seat.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequestDto {

  private List<String> horizontals;
  private String grade;
}
