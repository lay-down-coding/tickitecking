package com.laydowncoding.tickitecking.domain.concert.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.*;

import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertCreateRequestDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final AuditoriumRepository auditoriumRepository;

    @Override
    public void createConcert(Long companyUserId, ConcertCreateRequestDto requestDto) {
        validateAuditoriumId(requestDto.getAuditoriumId());

        Concert concert = Concert.builder()
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .startTime(requestDto.getStartTime())
            .companyUserId(companyUserId)
            .auditoriumId(requestDto.getAuditoriumId())
            .build();
        concertRepository.save(concert);
    }

    private void validateAuditoriumId(Long auditoriumId) {
        auditoriumRepository.findById(auditoriumId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_AUDITORIUM.getMessage())
        );
    }
}
