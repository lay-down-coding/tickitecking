package com.laydowncoding.tickitecking.domain.reservations.repository;

import static com.laydowncoding.tickitecking.domain.auditorium.entity.QAuditorium.auditorium;
import static com.laydowncoding.tickitecking.domain.concert.entitiy.QConcert.concert;
import static com.laydowncoding.tickitecking.domain.reservations.entity.QReservation.reservation;
import static com.laydowncoding.tickitecking.domain.seat.entity.QSeat.seat;
import static com.laydowncoding.tickitecking.domain.seat.entity.QSeatPrice.seatPrice;
import static com.laydowncoding.tickitecking.domain.user.entity.QUser.user;

import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminConcertResponseDto;
import com.laydowncoding.tickitecking.domain.admin.dto.response.AdminReservationResponseDto;
import com.laydowncoding.tickitecking.domain.reservations.entity.UnreservableSeat;
import com.laydowncoding.tickitecking.domain.seat.dto.response.SeatResponseDto;
import com.laydowncoding.tickitecking.domain.seat.entity.SeatStatus;
import com.laydowncoding.tickitecking.domain.user.dto.QUserReservationResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserReservationResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReservationRepositoryQueryImpl implements ReservationRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UnreservableSeat> findUnreservableSeats(Long concertId) {
        return jpaQueryFactory.select(seat.horizontal, seat.vertical, seat.seatStatus)
                .from(seat)
                .where(seat.seatStatus.eq(SeatStatus.LOCKED)
                        .or(seat.seatStatus.eq(SeatStatus.RESERVED))
                        .and(seat.concertId.eq(concertId)))
                .fetch()
                .stream()
                .map(tuple -> UnreservableSeat.builder()
                        .horizontal(tuple.get(seat.horizontal))
                        .vertical(tuple.get(seat.vertical))
                        .status(tuple.get(seat.seatStatus))
                        .build())
                .toList();
    }

    @Override
    public List<UserReservationResponseDto> findReservations(Long userId) {
        return jpaQueryFactory
                .select(
                        new QUserReservationResponseDto(concert.id,
                                concert.name,
                                concert.description,
                                concert.startTime,
                                auditorium.id,
                                auditorium.name,
                                auditorium.address,
                                seat.id,
                                seat.vertical,
                                seat.horizontal,
                                seat.grade,
                                seatPrice.price,
                                reservation.status,
                                reservation.deletedAt)
                )
                .from(reservation)
                .innerJoin(concert).on(reservation.concertId.eq(concert.id))
                .innerJoin(auditorium).on(auditorium.id.eq(concert.auditoriumId))
                .innerJoin(seat).on(seat.id.eq(reservation.seatId))
                .innerJoin(seatPrice)
                .on(seatPrice.concertId.eq(concert.id).and(seatPrice.grade.eq(seat.grade)))
                .where(reservation.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<AdminReservationResponseDto> getReservationAll() {
        return jpaQueryFactory.select(
                        Projections.constructor(
                                AdminReservationResponseDto.class,
                                reservation.id,
                                reservation.status,
                                Projections.constructor(
                                        UserResponseDto.class,
                                        user.id,
                                        user.username,
                                        user.email,
                                        user.nickname
                                ),
                                Projections.constructor(
                                        AdminConcertResponseDto.class,
                                        concert.id,
                                        concert.name,
                                        concert.description,
                                        concert.startTime
                                ),
                                Projections.constructor(
                                        SeatResponseDto.class,
                                        seat.id,
                                        seat.vertical,
                                        seat.horizontal,
                                        seat.seatStatus,
                                        seat.grade
                                ),
                                reservation.createdAt,
                                reservation.deletedAt
                        )
                ).from(reservation)
                .leftJoin(user).on(reservation.userId.eq(user.id))
                .leftJoin(concert).on(reservation.concertId.eq(concert.id))
                .leftJoin(seat).on(reservation.seatId.eq(seat.id))
                .fetch();
    }
}
