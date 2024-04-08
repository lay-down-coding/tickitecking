package com.laydowncoding.tickitecking.domain.seat.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatRequestDto {

  private List<String> horizontals;
  private String grade;
}
