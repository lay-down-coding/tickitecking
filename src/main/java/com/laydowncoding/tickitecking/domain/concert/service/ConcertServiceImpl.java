package com.laydowncoding.tickitecking.domain.concert.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.INVALID_COMPANY_USER_ID;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.NOT_FOUND_AUDITORIUM;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.NOT_FOUND_CONCERT;

import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.domain.seat.dto.AuditoriumCapacityDto;
import com.laydowncoding.tickitecking.domain.seat.dto.SeatPriceDto;
import com.laydowncoding.tickitecking.domain.seat.service.SeatService;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertServiceImpl implements ConcertService {

    private final ConcertRepository concertRepository;
    private final AuditoriumRepository auditoriumRepository;
    private final SeatService seatService;

    @Override
    public void createConcert(Long companyUserId, ConcertRequestDto requestDto) {
        Auditorium auditorium = findAuditorium(requestDto.getAuditoriumId());
        validateCompanyUserId(auditorium.getCompanyUserId(), companyUserId);

        Concert concert = Concert.builder()
            .name(requestDto.getName())
            .description(requestDto.getDescription())
            .startTime(requestDto.getStartTime())
            .companyUserId(companyUserId)
            .auditoriumId(requestDto.getAuditoriumId())
            .build();
        Concert saved = concertRepository.save(concert);

        AuditoriumCapacityDto capacityDto = AuditoriumCapacityDto.builder()
            .auditoriumId(auditorium.getId())
            .maxColumn(auditorium.getMaxColumn())
            .maxRow(auditorium.getMaxRow())
            .build();

        seatService.createSeats(requestDto.getSeatList(), saved.getId(), capacityDto);
        SeatPriceDto seatPriceDto = requestDto.getSeatPriceDto();
        seatService.createSeatPrices(saved.getId(), seatPriceDto);
    }

    @Override
    public ConcertResponseDto getConcert(Long concertId) {
        Concert concert = findConcert(concertId);
        SeatPriceDto seatPriceDto = seatService.getSeatPrices(concert.getId());

        Auditorium auditorium = findAuditorium(concert.getAuditoriumId());
        return ConcertResponseDto.builder()
            .id(concert.getId())
            .name(concert.getName())
            .description(concert.getDescription())
            .startTime(concert.getStartTime())
            .companyUserId(concert.getCompanyUserId())
            .auditoriumId(concert.getAuditoriumId())
            .auditoriumName(auditorium.getName())
            .auditoriumAddress(auditorium.getAddress())
            .auditoriumMaxColumn(auditorium.getMaxColumn())
            .auditoriumMaxRow(auditorium.getMaxRow())
            .goldPrice(seatPriceDto.getGoldPrice())
            .silverPrice(seatPriceDto.getSilverPrice())
            .bronzePrice(seatPriceDto.getBronzePrice())
            .build();
    }

    @Override
    public List<ConcertResponseDto> getAllConcerts() {
        List<Concert> concerts = concertRepository.findAll();

        List<ConcertResponseDto> concertResponseDtos = new ArrayList<>();
        for (Concert concert : concerts) {
            SeatPriceDto seatPriceDto = seatService.getSeatPrices(concert.getId());
            Auditorium auditorium = findAuditorium(concert.getAuditoriumId());
            ConcertResponseDto concertResponseDto = ConcertResponseDto.builder()
                .id(concert.getId())
                .name(concert.getName())
                .description(concert.getDescription())
                .startTime(concert.getStartTime())
                .companyUserId(concert.getCompanyUserId())
                .auditoriumId(concert.getAuditoriumId())
                .auditoriumName(auditorium.getName())
                .auditoriumAddress(auditorium.getAddress())
                .auditoriumMaxColumn(auditorium.getMaxColumn())
                .auditoriumMaxRow(auditorium.getMaxRow())
                .goldPrice(seatPriceDto.getGoldPrice())
                .silverPrice(seatPriceDto.getSilverPrice())
                .bronzePrice(seatPriceDto.getBronzePrice())
                .build();

            concertResponseDtos.add(concertResponseDto);
        }
        return concertResponseDtos;
    }

    @Override
    public ConcertResponseDto updateConcert(Long companyUserId, Long concertId,
        ConcertRequestDto requestDto) {
        Concert concert = findConcert(concertId);
        validateCompanyUserId(concert.getCompanyUserId(), companyUserId);
        Auditorium auditorium = findAuditorium(concert.getAuditoriumId());

        AuditoriumCapacityDto capacityDto = AuditoriumCapacityDto.builder()
            .auditoriumId(auditorium.getId())
            .maxColumn(auditorium.getMaxColumn())
            .maxRow(auditorium.getMaxRow())
            .build();

        concert.update(requestDto);
        seatService.updateSeats(requestDto.getSeatList(), concertId, capacityDto);
        SeatPriceDto seatPriceDto = seatService.updateSeatPrices(concertId,
            requestDto.getSeatPriceDto());

        return ConcertResponseDto.builder()
            .id(concert.getId())
            .name(concert.getName())
            .description(concert.getDescription())
            .startTime(concert.getStartTime())
            .companyUserId(concert.getCompanyUserId())
            .auditoriumId(concert.getAuditoriumId())
            .goldPrice(seatPriceDto.getGoldPrice())
            .silverPrice(seatPriceDto.getSilverPrice())
            .bronzePrice(seatPriceDto.getBronzePrice())
            .build();
    }

    @Override
    public void deleteConcert(Long companyUserId, Long concertId) {
        Concert concert = findConcert(concertId);
        validateCompanyUserId(concert.getCompanyUserId(), companyUserId);

        concertRepository.delete(concert);
        seatService.deleteSeats(concertId);
    }

    private void validateCompanyUserId(Long origin, Long input) {
        if (!Objects.equals(origin, input)) {
            throw new CustomRuntimeException(INVALID_COMPANY_USER_ID.getMessage());
        }
    }

    private Concert findConcert(Long concertId) {
        return concertRepository.findById(concertId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_CONCERT.getMessage())
        );
    }

    private Auditorium findAuditorium(Long auditoriumId) {
        return auditoriumRepository.findById(auditoriumId).orElseThrow(
            () -> new CustomRuntimeException(NOT_FOUND_AUDITORIUM.getMessage())
        );
    }
}
