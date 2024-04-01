package com.laydowncoding.tickitecking.domain.reservations.controller;

import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationRequestDto;
import com.laydowncoding.tickitecking.domain.reservations.dto.ReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.service.ReservationService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import com.laydowncoding.tickitecking.global.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/concerts/{concertId}/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<CommonResponse<ReservationResponseDto>> createReservation(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable Long concertId,
        @RequestBody ReservationRequestDto requestDto) {
        ReservationResponseDto response = reservationService.createReservation(
            userDetails.getUser().getId(), concertId, requestDto);
        return CommonResponse.ok(response);
    }
}
