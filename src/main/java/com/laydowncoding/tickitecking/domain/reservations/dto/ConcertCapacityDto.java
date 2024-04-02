package com.laydowncoding.tickitecking.domain.reservations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ConcertCapacityDto {

    private String maxColumn;
    private String maxRow;
}
