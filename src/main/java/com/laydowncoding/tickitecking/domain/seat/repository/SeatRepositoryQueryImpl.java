package com.laydowncoding.tickitecking.domain.seat.repository;

import static com.laydowncoding.tickitecking.domain.seat.entity.QSeat.seat;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SeatRepositoryQueryImpl implements SeatRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Boolean isReservable(Long concertId, String horizontal, String vertical) {
        String reserved = jpaQueryFactory.select(seat.reserved)
            .from(seat)
            .where(reservableCondition(concertId, horizontal, vertical))
            .fetchFirst();

        return reserved.equals("N");
    }

    private BooleanExpression reservableCondition(Long concertId, String horizontal, String vertical) {
        return seat.concertId.eq(concertId)
            .and(seat.vertical.eq(vertical))
            .and(seat.horizontal.eq(horizontal));
    }
}
