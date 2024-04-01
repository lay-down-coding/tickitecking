package com.laydowncoding.tickitecking.domain.reservations.controller;

import com.laydowncoding.tickitecking.domain.reservations.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events/{eventId}/reservations")
public class ReservationController {

    private final ReservationService reservationService;

}
