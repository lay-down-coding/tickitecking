package com.laydowncoding.tickitecking.domain.concert.service;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertCreateRequestDto;

public interface ConcertService {

    void createConcert(Long companyUserId, ConcertCreateRequestDto requestDto);
}
