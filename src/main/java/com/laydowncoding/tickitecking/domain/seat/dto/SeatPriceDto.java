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

    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
}
