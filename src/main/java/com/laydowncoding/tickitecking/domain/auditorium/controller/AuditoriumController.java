package com.laydowncoding.tickitecking.domain.auditorium.controller;

import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.service.AuditoriumService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.service.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auditoriums")
public class AuditoriumController {

  private final AuditoriumService auditoriumService;

  @PostMapping
  @Secured({"ROLE_COMPANY", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> createAuditorium(
      @RequestBody @Valid AuditoriumRequestDto auditoriumRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    auditoriumService.createAuditorium(auditoriumRequest, userDetails.getUser().getId());
    return CommonResponse.ok(null);
  }

  @PutMapping("/{auditoriumId}")
  @Secured({"ROLE_COMPANY", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> updateAuditorium(
      @RequestBody @Valid AuditoriumRequestDto auditoriumRequest,
      @PathVariable Long auditoriumId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    auditoriumService.updateAuditorium(auditoriumRequest, auditoriumId,
        userDetails.getUser().getId());
    return CommonResponse.ok(null);
  }

  @DeleteMapping("/{auditoriumId}")
  @Secured({"ROLE_COMPANY", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> deleteAuditorium(
      @PathVariable Long auditoriumId,
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    auditoriumService.deleteAuditorium(auditoriumId, userDetails.getUser().getId());
    return CommonResponse.ok(null);
  }

  @GetMapping
  @Secured({"ROLE_COMPANY"})
  public ResponseEntity<CommonResponse<List<AuditoriumResponseDto>>> getAuditoriums(
      @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    List<AuditoriumResponseDto> response = auditoriumService.getAuditoriums(
        userDetails.getUser().getId());
    return CommonResponse.ok(response);
  }

  @GetMapping("/{auditoriumId}")
  public ResponseEntity<CommonResponse<AuditoriumResponseDto>> getAuditorium(
      @PathVariable Long auditoriumId
  ) {
    AuditoriumResponseDto response = auditoriumService.getAuditorium(auditoriumId);
    return CommonResponse.ok(response);
  }
}
