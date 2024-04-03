package com.laydowncoding.tickitecking.domain.reservations.dto;

import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ConcertSeatResponseDto {

    private ConcertInfoDto concertInfoDto;
    private List<UnreservableSeat> unreservableSeats;
}
