package com.laydowncoding.tickitecking.domain.seat.service;

import com.laydowncoding.tickitecking.domain.seat.dto.SeatPriceDto;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatPrice;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatPriceRepository;
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

    @Override
    public void createSeatPrices(Long concertId, SeatPriceDto seatPriceDto) {
        List<SeatPrice> seatPrices = new ArrayList<>();

        Map<Character, Double> seatPricesMap = parseSeatPrices(seatPriceDto);

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

        Map<Character, Double> seatPricesMap = parseSeatPrices(seatPriceDto);
        seatPrices.forEach(seatPrice -> {
            seatPrice.update(seatPrice.getGrade(), seatPricesMap.get(seatPrice.getGrade()));
        });

        return SeatPriceDto.builder()
            .goldPrice(seatPricesMap.get('G'))
            .silverPrice(seatPricesMap.get('S'))
            .bronzePrice(seatPricesMap.get('B'))
            .build();
    }

    @Override
    public SeatPriceDto getSeatPrices(Long concertId) {
        List<SeatPrice> seatPrices = seatPriceRepository.findAllByConcertId(concertId);
        Map<Character, Double> seatPricesMap = new HashMap<>();
        for (SeatPrice seatPrice : seatPrices) {
            seatPricesMap.put(seatPrice.getGrade(), seatPrice.getPrice());
        }

        return SeatPriceDto.builder()
            .goldPrice(seatPricesMap.get('G'))
            .silverPrice(seatPricesMap.get('S'))
            .bronzePrice(seatPricesMap.get('B'))
            .build();
    }

    private Map<Character, Double> parseSeatPrices(SeatPriceDto seatPriceDto) {
        return Map.of(
            'G', seatPriceDto.getGoldPrice(),
            'S', seatPriceDto.getSilverPrice(),
            'B', seatPriceDto.getBronzePrice()
        );
    }
}
