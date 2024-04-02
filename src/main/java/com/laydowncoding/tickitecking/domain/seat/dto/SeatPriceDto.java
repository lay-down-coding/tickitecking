package com.laydowncoding.tickitecking.domain.seat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatPriceDto {

    private Double goldPrice;
    private Double silverPrice;
    private Double bronzePrice;
}
