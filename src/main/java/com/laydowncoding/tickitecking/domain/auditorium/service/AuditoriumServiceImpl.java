package com.laydowncoding.tickitecking.domain.auditorium.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.INVALID_COMPANY_USER_ID;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.NOT_FOUND_AUDITORIUM;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.Auditorium;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;

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
    }

    @Override
    @Transactional
    public void deleteAuditorium(Long auditoriumId, Long userId) {
        Auditorium auditorium = findAuditorium(auditoriumId);

        checkWritingUser(auditorium.getCompanyUserId(), userId);

        auditoriumRepository.delete(auditorium);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditoriumResponseDto> getAuditoriums(Long userId) {
        return auditoriumRepository.getAuditoriumAllByCompanyUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public AuditoriumResponseDto getAuditorium(Long auditoriumId) {
        return auditoriumRepository.getAuditoriumByAuditoriumId(auditoriumId);
    }

    private Auditorium findAuditorium(Long auditoriumId) {
        return auditoriumRepository.findById(auditoriumId).orElseThrow(
                () -> new CustomRuntimeException(NOT_FOUND_AUDITORIUM.getMessage())
        );
    }

    private void checkWritingUser(Long writerId, Long userId) {
        if (!userId.equals(writerId)) {
            throw new CustomRuntimeException(INVALID_COMPANY_USER_ID.getMessage());
        }
    }
}
