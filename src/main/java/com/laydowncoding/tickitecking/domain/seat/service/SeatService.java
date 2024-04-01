package com.laydowncoding.tickitecking.domain.seat.service;

import com.laydowncoding.tickitecking.domain.seat.dto.SeatPriceDto;

public interface SeatService {

    void createSeatPrices(Long concertId, SeatPriceDto seatPriceDto);
}
