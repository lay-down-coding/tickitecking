package com.laydowncoding.tickitecking.domain.auditorium.service;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumQueryRepository;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.seat.dto.request.SeatRequestDto;
import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.laydowncoding.tickitecking.domain.seat.repository.SeatRepository;
import com.laydowncoding.tickitecking.global.exception.InvalidUserException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

  private final AuditoriumRepository auditoriumRepository;
  private final SeatRepository seatRepository;

  @Override
  @Transactional
  public void createAuditorium(AuditoriumRequestDto auditoriumRequest, Long userId) {
    Auditorium auditorium = new Auditorium(
        auditoriumRequest.getName(),
        auditoriumRequest.getAddress(),
        auditoriumRequest.getMaxColumn(),
        auditoriumRequest.getMaxRow(),
        userId
    );

    auditoriumRepository.save(auditorium);

    List<Seat> seatList = new ArrayList<>();

    for (SeatRequestDto seatRequest : auditoriumRequest.getSeatList()) {
      for (String horizontal : seatRequest.getHorizontals()) {
        for (int vertical = 1; vertical <= Integer.parseInt(auditoriumRequest.getMaxColumn());
            vertical++) {
          Seat seat = new Seat(
              String.valueOf(vertical),
              horizontal,
              auditoriumRequest.getAvailability(),
              seatRequest.getGrade(),
              auditorium.getId()
          );
          seatList.add(seat);
        }
      }
    }

    seatRepository.saveAll(seatList);
  }

  @Override
  @Transactional
  public void updateAuditorium(AuditoriumRequestDto auditoriumRequest, Long auditoriumId,
      Long userId) {
    Auditorium auditorium = findAuditorium(auditoriumId);

    checkWritingUser(auditorium.getCompanyUserId(), userId);

    auditorium.update(
        auditoriumRequest.getName(),
        auditoriumRequest.getAddress(),
        auditoriumRequest.getMaxColumn(),
        auditoriumRequest.getMaxRow()
    );

    for (SeatRequestDto seatRequest : auditoriumRequest.getSeatList()) {
      for (String horizontal : seatRequest.getHorizontals()) {
        for (int vertical = 1; vertical <= Integer.parseInt(auditoriumRequest.getMaxColumn());
            vertical++) {
          Seat seat = seatRepository.findByAuditoriumIdAndHorizontalAndVertical(auditoriumId,
              horizontal, String.valueOf(vertical));
          if (seat != null) {
            seat.update(seatRequest.getGrade());
          }
        }
      }
    }
  }

  @Override
  @Transactional
  public void deleteAuditorium(Long auditoriumId, Long userId) {
    Auditorium auditorium = findAuditorium(auditoriumId);

    checkWritingUser(auditorium.getCompanyUserId(), userId);

    auditoriumRepository.delete(auditorium);

    List<Seat> seatList = seatRepository.findAllByAuditoriumId(auditorium.getId());

    seatRepository.deleteAll(seatList);
  }

  @Override
  public List<AuditoriumResponseDto> getAuditoriums(Long userId) {
    return auditoriumRepository.getAuditoriumAllByCompanyUserId(userId);
  }

  @Override
  public AuditoriumResponseDto getAuditorium(Long auditoriumId) {
    return auditoriumRepository.getAuditoriumByAuditoriumId(auditoriumId);
  }

  public Auditorium findAuditorium(Long auditoriumId) {
    return auditoriumRepository.findById(auditoriumId).orElseThrow(
        () -> new NullPointerException("존재하지 않습니다.")
    );
  }

  public void checkWritingUser(Long writerId, Long userId) {
    if (!userId.equals(writerId)) {
      throw new InvalidUserException("작성자가 아닙니다.");
    }
  }
}
