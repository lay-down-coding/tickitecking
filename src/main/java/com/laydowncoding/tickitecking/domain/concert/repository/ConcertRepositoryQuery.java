package com.laydowncoding.tickitecking.domain.concert.repository;

import com.laydowncoding.tickitecking.domain.concert.dto.AllConcertResponseDto;
import java.util.List;

public interface ConcertRepositoryQuery {

  List<AllConcertResponseDto> getAllConcerts();
}
