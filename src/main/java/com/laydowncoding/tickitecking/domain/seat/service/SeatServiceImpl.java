package com.laydowncoding.tickitecking.domain.seat.service;

import com.laydowncoding.tickitecking.domain.seat.dto.AuditoriumCapacityDto;
import com.laydowncoding.tickitecking.domain.seat.dto.SeatPriceDto;
import com.laydowncoding.tickitecking.domain.seat.dto.request.SeatRequestDto;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatPrice;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatPriceRepository;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {

    private final SeatPriceRepository seatPriceRepository;
    private final SeatRepository seatRepository;

    @Override
    public void createSeats(List<SeatRequestDto> seatRequestDtos, Long concertId,
        AuditoriumCapacityDto capacityDto) {
        List<Seat> seatList = new ArrayList<>();

        for (SeatRequestDto seatRequest : seatRequestDtos) {
            for (String horizontal : seatRequest.getHorizontals()) {
                for (int vertical = 1; vertical <= Integer.parseInt(capacityDto.getMaxColumn());
                    vertical++) {
                    Seat seat = Seat.builder()
                        .vertical(String.valueOf(vertical))
                        .horizontal(horizontal)
                        .grade(seatRequest.getGrade())
                        .auditoriumId(capacityDto.getAuditoriumId())
                        .concertId(concertId)
                        .reserved("N")
                        .build();
                    seatList.add(seat);
                }
            }
        }

        seatRepository.saveAll(seatList);
    }

    @Override
    public void updateSeats(List<SeatRequestDto> seatRequestDtos, Long concertId,
        AuditoriumCapacityDto capacityDto) {
        for (SeatRequestDto seatRequest : seatRequestDtos) {
            for (String horizontal : seatRequest.getHorizontals()) {
                for (int vertical = 1; vertical <= Integer.parseInt(capacityDto.getMaxColumn());
                    vertical++) {
                    Seat seat = seatRepository.findByConcertIdAndHorizontalAndVertical(
                        concertId, horizontal, String.valueOf(vertical));
                    if (seat != null) {
                        seat.update(seatRequest.getGrade());
                    }
                }
            }
        }
    }

    @Override
    public void deleteSeats(Long concertId) {
        List<Seat> seatList = seatRepository.findAllByConcertId(concertId);

        seatRepository.deleteAll(seatList);
    }

    @Override
    public void createSeatPrices(Long concertId, SeatPriceDto seatPriceDto) {
        List<SeatPrice> seatPrices = new ArrayList<>();

        Map<String, Double> seatPricesMap = parseSeatPrices(seatPriceDto);

        seatPricesMap.forEach((grade, price) -> {
            SeatPrice seatPrice = SeatPrice.builder()
                .price(price)
                .grade(grade)
                .concertId(concertId)
                .build();
            seatPrices.add(seatPrice);
        });

        seatPriceRepository.saveAll(seatPrices);
    }

    @Override
    public SeatPriceDto updateSeatPrices(Long concertId, SeatPriceDto seatPriceDto) {
        List<SeatPrice> seatPrices = seatPriceRepository.findAllByConcertId(concertId);

        Map<String, Double> seatPricesMap = parseSeatPrices(seatPriceDto);
        seatPrices.forEach(seatPrice -> {
            seatPrice.update(seatPrice.getGrade(), seatPricesMap.get(seatPrice.getGrade()));
        });

        return SeatPriceDto.builder()
            .goldPrice(seatPricesMap.get("G"))
            .silverPrice(seatPricesMap.get("S"))
            .bronzePrice(seatPricesMap.get("B"))
            .build();
    }

    @Override
    public SeatPriceDto getSeatPrices(Long concertId) {
        List<SeatPrice> seatPrices = seatPriceRepository.findAllByConcertId(concertId);
        Map<String, Double> seatPricesMap = new HashMap<>();
        for (SeatPrice seatPrice : seatPrices) {
            seatPricesMap.put(seatPrice.getGrade(), seatPrice.getPrice());
        }

        return SeatPriceDto.builder()
            .goldPrice(seatPricesMap.getOrDefault("G", 0.0))
            .silverPrice(seatPricesMap.getOrDefault("S", 0.0))
            .bronzePrice(seatPricesMap.getOrDefault("B", 0.0))
            .build();
    }

    private Map<String, Double> parseSeatPrices(SeatPriceDto seatPriceDto) {
        return Map.of(
            "G", seatPriceDto.getGoldPrice(),
            "S", seatPriceDto.getSilverPrice(),
            "B", seatPriceDto.getBronzePrice()
        );
    }
}
