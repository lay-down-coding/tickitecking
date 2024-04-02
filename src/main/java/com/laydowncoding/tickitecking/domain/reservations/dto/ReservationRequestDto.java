package com.laydowncoding.tickitecking.domain.reservations.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequestDto {

    private String vertical;
    private String horizontal;
}
