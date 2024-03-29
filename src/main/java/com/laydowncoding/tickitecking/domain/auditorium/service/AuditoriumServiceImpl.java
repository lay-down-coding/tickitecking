package com.laydowncoding.tickitecking.domain.auditorium.service;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.global.exception.InvalidUserException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

  private final AuditoriumRepository auditoriumRepository;

  @Override
  @Transactional
  public void createAuditorium(AuditoriumRequestDto auditoriumRequest) {
    // 유저 아이디 임시
    Auditorium auditorium = new Auditorium(
        auditoriumRequest.getName(),
        auditoriumRequest.getAddress(),
        auditoriumRequest.getMaxColumn(),
        auditoriumRequest.getMaxRow(),
        1L
    );

    auditoriumRepository.save(auditorium);
  }

  @Override
  @Transactional
  public void updateAuditorium(AuditoriumRequestDto auditoriumRequest, Long auditoriumId) {
    Auditorium auditorium = findAuditorium(auditoriumId);

    // 유저 검증 필요
    checkWritingUser(auditorium.getCompanyUserId(), 1L);

    auditorium.update(
        auditoriumRequest.getName(),
        auditoriumRequest.getAddress(),
        auditoriumRequest.getMaxColumn(),
        auditoriumRequest.getMaxRow()
    );
  }

  @Override
  @Transactional
  public void deleteAuditorium(Long auditoriumId) {
    Auditorium auditorium = findAuditorium(auditoriumId);

    checkWritingUser(auditorium.getCompanyUserId(), 1L);

    auditoriumRepository.delete(auditorium);
  }

  @Override
  public List<AuditoriumResponseDto> getAuditoriums() {
    List<Auditorium> auditoriumList = auditoriumRepository.findAllByCompanyUserId(1L);

    if (auditoriumList.isEmpty()) {
      throw new NullPointerException("존재하지 않습니다.");
    }

    return auditoriumList.stream().map(auditorium -> new AuditoriumResponseDto(
        auditorium.getId(),
        auditorium.getName(),
        auditorium.getAddress(),
        auditorium.getMaxColumn(),
        auditorium.getMaxRow(),
        auditorium.getCompanyUserId()
    )).collect(Collectors.toList());
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
