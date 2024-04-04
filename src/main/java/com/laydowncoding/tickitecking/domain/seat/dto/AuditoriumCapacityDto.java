package com.laydowncoding.tickitecking.domain.seat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditoriumCapacityDto {

    private Long auditoriumId;
    private String maxColumn;
    private String maxRow;
}
