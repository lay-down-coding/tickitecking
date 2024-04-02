package com.laydowncoding.tickitecking.domain.reservations.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class UnreservableSeat {

    private final String horizontal;
    private final String vertical;
    private final boolean isReserved;
    private final boolean isLocked;
}
