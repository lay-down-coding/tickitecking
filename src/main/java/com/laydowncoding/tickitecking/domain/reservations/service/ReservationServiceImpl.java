package com.laydowncoding.tickitecking.domain.reservations.service;

import com.laydowncoding.tickitecking.domain.reservations.repository.ReservationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationsRepository reservationsRepository;
}
