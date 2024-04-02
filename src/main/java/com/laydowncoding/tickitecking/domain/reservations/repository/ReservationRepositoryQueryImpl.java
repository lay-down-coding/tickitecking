package com.laydowncoding.tickitecking.domain.reservations.repository;

import static com.laydowncoding.tickitecking.domain.auditorium.entity.QAuditorium.*;
import static com.laydowncoding.tickitecking.domain.concert.entitiy.QConcert.*;
import static com.laydowncoding.tickitecking.domain.reservations.entity.QReservation.*;
import static com.laydowncoding.tickitecking.domain.seat.entity.QSeat.*;

import com.laydowncoding.tickitecking.domain.reservations.dto.ConcertCapacityDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationRepositoryQueryImpl implements ReservationRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UnreservableSeat> findUnreservableSeats(Long concertId) {
        List<Tuple> reserved = findReservedSeats(concertId);
        List<Tuple> locked = findLockedSeats(concertId);
        List<UnreservableSeat> unreservableSeats = new ArrayList<>();

        for (Tuple tuple : reserved) {
            String horizontal = tuple.get(seat.horizontal);
            String vertical = tuple.get(seat.vertical);

            UnreservableSeat unreservableSeat = UnreservableSeat.builder()
                .horizontal(horizontal)
                .vertical(vertical)
                .isReserved(true)
                .isLocked(false)
                .build();
            unreservableSeats.add(unreservableSeat);
        }

        for (Tuple tuple : locked) {
            String horizontal = tuple.get(seat.horizontal);
            String vertical = tuple.get(seat.vertical);

            UnreservableSeat unreservableSeat = UnreservableSeat.builder()
                .horizontal(horizontal)
                .vertical(vertical)
                .isReserved(false)
                .isLocked(true)
                .build();
            unreservableSeats.add(unreservableSeat);
        }
        return unreservableSeats;
    }

    @Override
    public List<Tuple> findReservedSeats(Long concertId) {
        return jpaQueryFactory.select(seat.horizontal, seat.vertical)
            .from(concert)
            .join(seat).on(concert.auditoriumId.eq(seat.auditoriumId))
            .join(reservation).on(reservation.seatId.eq(seat.id))
            .where(reservedSeatCondition(concertId))
            .fetch();
    }

    @Override
    public ConcertCapacityDto findCapacity(Long concertId) {
        Tuple tuple = jpaQueryFactory.select(auditorium.maxColumn, auditorium.maxRow)
            .from(auditorium)
            .join(concert).on(concert.auditoriumId.eq(auditorium.id))
            .where(concert.id.eq(concertId))
            .fetchOne();
        assert tuple != null;
        return ConcertCapacityDto.builder()
            .maxColumn(tuple.get(auditorium.maxColumn))
            .maxRow(tuple.get(auditorium.maxRow))
            .build();
    }

    @Override
    public List<Tuple> findLockedSeats(Long concertId) {
        return jpaQueryFactory.select(seat.horizontal, seat.vertical)
            .from(concert)
            .join(seat).on(concert.auditoriumId.eq(seat.auditoriumId))
            .join(reservation).on(reservation.seatId.eq(seat.id))
            .where(lockedSeatCondition(concertId))
            .fetch();
    }

    private BooleanExpression reservedSeatCondition(Long concertId) {
        return concert.id.eq(concertId)
            .and(reservation.concertId.eq(concertId))
            .and(reservation.seatId.eq(seat.id))
            .and(reservation.status.eq("Y"));
    }

    private BooleanExpression lockedSeatCondition(Long concertId) {
        return concert.id.eq(concertId)
            .and(reservation.concertId.eq(concertId))
            .and(reservation.seatId.eq(seat.id))
            .and(seat.availability.eq("N"));
    }
}
