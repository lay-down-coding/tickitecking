package com.laydowncoding.tickitecking.domain.concert.controller;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertCreateRequestDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.dto.ConcertUpdateRequestDto;
import com.laydowncoding.tickitecking.domain.concert.service.ConcertService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.service.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

//  @Secured("ROLE_COMPANY_USER")
  @PostMapping
  public ResponseEntity<CommonResponse<Void>> createConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @RequestBody ConcertCreateRequestDto requestDto) {
    concertService.createConcert(userDetails.getUser().getId(), requestDto);
    return CommonResponse.ok(null);
  }

  //@Secured("ROLE_COMPANY_USER")
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
  public ResponseEntity<CommonResponse<ConcertResponseDto>> updateConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long concertId,
      @RequestBody ConcertUpdateRequestDto requestDto) {
    concertService.updateConcert(userDetails.getUser().getId(), concertId, requestDto);
    return CommonResponse.ok(null);
  }

  @DeleteMapping("/{concertId}")
  public ResponseEntity<CommonResponse<Void>> deleteConcert(
      @AuthenticationPrincipal UserDetailsImpl userDetails,
      @PathVariable Long concertId) {
    concertService.deleteConcert(userDetails.getUser().getId(), concertId);
    return CommonResponse.ok(null);
  }
}

