package com.laydowncoding.tickitecking.domain.concert.controller;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.service.ConcertService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.service.UserDetailsImpl;
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
@RequestMapping("/api/v1/concerts")
public class ConcertController {

  private final ConcertService concertService;

  @PostMapping
  @Secured({"ROLE_COMPANY_USER", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> createConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ConcertRequestDto requestDto) {
    concertService.createConcert(userDetails.getUser().getId(), requestDto);
    return CommonResponse.ok(null);
  }

  @GetMapping("/{concertId}")
  public ResponseEntity<CommonResponse<ConcertResponseDto>> getConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long concertId) {
    ConcertResponseDto response = concertService.getConcert(concertId);
    return CommonResponse.ok(response);
  }

  @GetMapping
  public ResponseEntity<CommonResponse<List<ConcertResponseDto>>> getAllConcerts(
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    List<ConcertResponseDto> response = concertService.getAllConcerts();
    return CommonResponse.ok(response);
  }

  @PutMapping("/{concertId}")
  @Secured({"ROLE_COMPANY_USER", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<ConcertResponseDto>> updateConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long concertId,
      @RequestBody ConcertRequestDto requestDto) {
    ConcertResponseDto responseDto = concertService.updateConcert(userDetails.getUser().getId(),
        concertId, requestDto);
    return CommonResponse.ok(responseDto);
  }

  @DeleteMapping("/{concertId}")
  @Secured({"ROLE_COMPANY_USER", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> deleteConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long concertId) {
    concertService.deleteConcert(userDetails.getUser().getId(), concertId);
    return CommonResponse.ok(null);
  }
}

