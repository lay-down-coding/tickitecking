package com.laydowncoding.tickitecking.domain.seat.repository;

import static com.laydowncoding.tickitecking.domain.concert.entitiy.QConcert.*;
import static com.laydowncoding.tickitecking.domain.reservations.entity.QReservation.*;
import static com.laydowncoding.tickitecking.domain.seat.entity.QSeat.*;

import com.laydowncoding.tickitecking.domain.reservations.entity.Reservation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeatRepositoryQueryImpl implements SeatRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Boolean isReservable(Long concertId, String horizontal, String vertical) {
        List<Reservation> fetch = jpaQueryFactory.select(reservation)
            .from(concert)
            .join(seat).on(concert.auditoriumId.eq(seat.auditoriumId))
            .join(reservation).on(reservation.seatId.eq(seat.id))
            .where(reservableCondition(concertId, horizontal, vertical))
            .fetch();
        return fetch.isEmpty();
    }

    @Override
    public Long findSeatId(Long concertId, String horizontal, String vertical) {
        return jpaQueryFactory.select(seat.id)
            .from(concert)
            .join(seat).on(concert.auditoriumId.eq(seat.auditoriumId))
            .where(findSeatCondition(concertId, horizontal, vertical))
            .fetchOne();
    }

    private BooleanExpression reservableCondition(Long concertId, String horizontal, String vertical) {
        return concert.id.eq(concertId)
            .and(seat.horizontal.eq(horizontal))
            .and(seat.vertical.eq(vertical))
            .and(concert.auditoriumId.eq(seat.auditoriumId))
            .and(reservation.concertId.eq(concertId))
            .and(reservation.status.eq("Y"));
    }

    private BooleanExpression findSeatCondition(Long concertId, String horizontal, String vertical) {
        return concert.id.eq(concertId)
            .and(seat.horizontal.eq(horizontal))
            .and(seat.vertical.eq(vertical));
    }
}
