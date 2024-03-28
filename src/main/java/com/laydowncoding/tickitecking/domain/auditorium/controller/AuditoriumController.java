package com.laydowncoding.tickitecking.domain.auditorium.controller;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.service.AuditoriumService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auditoriums")
public class AuditoriumController {

  private final AuditoriumService auditoriumService;

  @PostMapping
  public ResponseEntity<CommonResponse<Void>> createAuditorium(
      @RequestBody @Valid AuditoriumRequestDto auditoriumRequest
  ) {
    auditoriumService.createAuditorium(
        auditoriumRequest.getName(),
        auditoriumRequest.getAddress(),
        auditoriumRequest.getMaxColumn(),
        auditoriumRequest.getMaxRow()
    );
    return CommonResponse.ok(null);
  }
}
