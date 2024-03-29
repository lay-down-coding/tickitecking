package com.laydowncoding.tickitecking.domain.auditorium.service;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.global.exception.InvalidUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService{

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
