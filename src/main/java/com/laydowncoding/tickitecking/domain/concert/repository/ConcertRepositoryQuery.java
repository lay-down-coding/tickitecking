package com.laydowncoding.tickitecking.domain.concert.repository;

import com.laydowncoding.tickitecking.domain.concert.dto.AllConcertResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ConcertRepositoryQuery {

    Page<AllConcertResponseDto> getAllConcerts(Pageable pageable);
}
