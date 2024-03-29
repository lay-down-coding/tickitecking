package com.laydowncoding.tickitecking.domain.auditorium.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriumResponseDto {

  private Long id;

  private String name;

  private String address;

  private String maxColumn;

  private String maxRow;

  private Long companyUserId;
}
