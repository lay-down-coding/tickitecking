package com.laydowncoding.tickitecking.domain.seat.service;

import com.laydowncoding.tickitecking.domain.seat.dto.SeatPriceDto;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatPrice;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatPriceRepository;
import java.util.ArrayList;
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

        Map<Character, Double> seatPricesMap = Map.of(
            'G', seatPriceDto.getGoldPrice(),
            'S', seatPriceDto.getSilverPrice(),
            'B', seatPriceDto.getBronzePrice()
        );

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
}
