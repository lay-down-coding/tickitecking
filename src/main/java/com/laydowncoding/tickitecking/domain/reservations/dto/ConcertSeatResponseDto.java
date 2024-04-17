package com.laydowncoding.tickitecking.domain.reservations.dto;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ConcertSeatResponseDto {

    private ConcertResponseDto concertResponseDto;
    private List<UnreservableSeat> unreservableSeats;
}
