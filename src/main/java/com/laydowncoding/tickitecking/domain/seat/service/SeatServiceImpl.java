package com.laydowncoding.tickitecking.domain.seat.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.SeatErrorCode.INVALID_HORIZONTAL;

import com.laydowncoding.tickitecking.domain.seat.dto.AuditoriumCapacityDto;
import com.laydowncoding.tickitecking.domain.seat.dto.request.SeatPriceRequestDto;
import com.laydowncoding.tickitecking.domain.seat.dto.request.SeatRequestDto;
import com.laydowncoding.tickitecking.domain.seat.dto.response.SeatPriceResponseDto;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatPrice;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatPriceRepository;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
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
    List<Seat> seatList = seatRequestDtos.stream()
        .flatMap(seatRequest -> seatRequest.getHorizontals().stream()
            .filter(horizontal -> isValidHorizontal(horizontal, capacityDto.getMaxRow()))
            .flatMap(horizontal -> generateSeats(concertId, capacityDto, horizontal,
                seatRequest.getGrade())))
        .collect(Collectors.toList());

    seatRepository.saveAllSeat(seatList);
  }

  @Override
  public void updateSeats(List<SeatRequestDto> seatRequestDtos, Long concertId,
      AuditoriumCapacityDto capacityDto) {
    List<Seat> seatList = seatRepository.findAllByConcertIdAndAuditoriumId(concertId,
        capacityDto.getAuditoriumId());

    Set<String> requiredHorizontals = extractValidHorizontals(seatRequestDtos,
        capacityDto.getMaxRow());
    Set<Integer> requiredVerticals = rangeToSet(1, Integer.parseInt(capacityDto.getMaxColumn()));

    List<Seat> updateSeatList = filterSeats(seatList, requiredHorizontals, requiredVerticals);
    updateSeatGrades(updateSeatList, seatRequestDtos, requiredVerticals);
  }

  @Override
  public void deleteSeats(Long concertId) {
    List<Seat> seatList = seatRepository.findAllByConcertId(concertId);

    seatRepository.deleteAll(seatList);
  }

  @Override
  public void createSeatPrices(Long concertId, List<SeatPriceRequestDto> seatPriceRequestDtos) {
    List<SeatPrice> seatPrices = seatPricesToEntity(concertId, seatPriceRequestDtos);
    seatPriceRepository.saveAll(seatPrices);
  }

  @Override
  public List<SeatPriceResponseDto> updateSeatPrices(Long concertId,
      List<SeatPriceRequestDto> seatPriceRequestDtos) {
    seatPriceRepository.deleteAllByConcertId(concertId);
    List<SeatPrice> seatPrices = seatPricesToEntity(concertId, seatPriceRequestDtos);
    List<SeatPrice> saved = seatPriceRepository.saveAll(seatPrices);
    return saved.stream()
        .map(seatPrice -> new SeatPriceResponseDto(seatPrice.getGrade(), seatPrice.getPrice()))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<SeatPriceResponseDto> getSeatPrices(Long concertId) {
    List<SeatPrice> seatPrices = seatPriceRepository.findAllByConcertId(concertId);
    return seatPrices.stream()
        .map(seatPrice -> new SeatPriceResponseDto(seatPrice.getGrade(), seatPrice.getPrice()))
        .toList();
  }

  @Override
  public void deleteSeatPrices(Long concertId) {
    seatPriceRepository.deleteAllByConcertId(concertId);
  }

  private List<SeatPrice> seatPricesToEntity(Long concertId,
      List<SeatPriceRequestDto> seatPriceRequestDtos) {

    List<SeatPrice> seatPrices = new ArrayList<>();

    for (SeatPriceRequestDto requestDto : seatPriceRequestDtos) {
      SeatPrice seatPrice = SeatPrice.builder()
          .grade(requestDto.getGrade())
          .price(requestDto.getPrice())
          .concertId(concertId)
          .build();
      seatPrices.add(seatPrice);
    }
    return seatPrices;
  }

  private boolean isValidHorizontal(String horizontal, String maxRow) {
    char horizontalChar = horizontal.charAt(0);
    char maxRowChar = maxRow.charAt(0);
    if (horizontalChar > maxRowChar) {
      throw new CustomRuntimeException(INVALID_HORIZONTAL.getMessage());
    }
    return true;
  }

  private Stream<Seat> generateSeats(Long concertId, AuditoriumCapacityDto capacityDto,
      String horizontal, String grade) {
    int maxColumn = Integer.parseInt(capacityDto.getMaxColumn());
    return IntStream.rangeClosed(1, maxColumn)
        .mapToObj(vertical -> createSeat(concertId, capacityDto, horizontal, vertical, grade));
  }

  private Seat createSeat(Long concertId, AuditoriumCapacityDto capacityDto, String horizontal,
      int vertical, String grade) {
    return Seat.builder()
        .vertical(String.valueOf(vertical))
        .horizontal(horizontal)
        .grade(grade)
        .auditoriumId(capacityDto.getAuditoriumId())
        .concertId(concertId)
        .build();
  }

  private Set<String> extractValidHorizontals(List<SeatRequestDto> seatRequestDtos, String maxRow) {
    return seatRequestDtos.stream()
        .flatMap(dto -> dto.getHorizontals().stream())
        .filter(horizontal -> isValidHorizontal(horizontal, maxRow))
        .collect(Collectors.toSet());
  }

  private Set<Integer> rangeToSet(int start, int end) {
    return IntStream.rangeClosed(start, end)
        .boxed()
        .collect(Collectors.toSet());
  }

  private List<Seat> filterSeats(List<Seat> seats, Set<String> requiredHorizontals,
      Set<Integer> requiredVerticals) {
    return seats.stream()
        .filter(seat -> requiredHorizontals.contains(seat.getHorizontal()) &&
            requiredVerticals.contains(Integer.parseInt(seat.getVertical())))
        .collect(Collectors.toList());
  }

  private void updateSeatGrades(List<Seat> seats, List<SeatRequestDto> seatRequestDtos,
      Set<Integer> requiredVerticals) {
    seats.forEach(seat -> {
      seatRequestDtos.forEach(dto -> {
        dto.getHorizontals().stream()
            .filter(horizontal -> horizontal.equals(seat.getHorizontal()))
            .forEach(horizontal -> {
              if (requiredVerticals.contains(Integer.parseInt(seat.getVertical()))) {
                seat.update(dto.getGrade());
              }
            });
      });
    });

    seatRepository.updateAllSeat(seats);
  }
}
