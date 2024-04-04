package com.laydowncoding.tickitecking.domain.concert.service;

import com.laydowncoding.tickitecking.domain.concert.dto.AllConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import java.util.List;

public interface ConcertService {

    void createConcert(Long companyUserId, ConcertRequestDto requestDto);

    ConcertResponseDto getConcert(Long concertId);

    List<AllConcertResponseDto> getAllConcerts();

    ConcertResponseDto updateConcert(Long companyUserId, Long concertId, ConcertRequestDto requestDto);

    void deleteConcert(Long id, Long concertId);
}
