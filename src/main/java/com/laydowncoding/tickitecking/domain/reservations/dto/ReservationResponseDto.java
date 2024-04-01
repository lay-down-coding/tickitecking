package com.laydowncoding.tickitecking.domain.reservations.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {

    private Long id;
    private String status;
    private Long userId;
    private Long eventId;
    private Long seatId;
}
