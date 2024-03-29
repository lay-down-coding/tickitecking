package com.laydowncoding.tickitecking.domain.concert.controller;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertCreateRequestDto;
import com.laydowncoding.tickitecking.domain.concert.service.ConcertService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
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
}
