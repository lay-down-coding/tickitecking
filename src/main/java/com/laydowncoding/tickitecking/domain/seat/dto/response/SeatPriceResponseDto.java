package com.laydowncoding.tickitecking.domain.seat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SeatPriceResponseDto {

    private String grade;
    private Double price;
}
