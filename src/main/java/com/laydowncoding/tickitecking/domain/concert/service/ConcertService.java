package com.laydowncoding.tickitecking.domain.concert.service;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertCreateRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import java.util.List;

public interface ConcertService {

    void createConcert(Long companyUserId, ConcertCreateRequestDto requestDto);

    ConcertResponseDto getConcert(Long concertId);

    List<ConcertResponseDto> getAllConcerts();
}
