package com.laydowncoding.tickitecking.domain.concert.repository;

import static com.laydowncoding.tickitecking.domain.auditorium.entity.QAuditorium.auditorium;
import static com.laydowncoding.tickitecking.domain.concert.entitiy.QConcert.concert;
import static com.laydowncoding.tickitecking.domain.image.entity.QImage.image;
import static com.laydowncoding.tickitecking.domain.user.entity.QUser.user;

import com.laydowncoding.tickitecking.domain.concert.dto.AllConcertResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ConcertRepositoryImpl implements ConcertRepositoryQuery {

  private final JPAQueryFactory query;


  @Override
  public List<AllConcertResponseDto> getAllConcerts() {
    return query.select(
            Projections.constructor(
                AllConcertResponseDto.class,
                concert.id,
                concert.name,
                image.filePath,
                concert.startTime,
                user.nickname,
                auditorium.name
            )
        )
        .from(concert)
        .join(user).on(concert.companyUserId.eq(user.id))
        .join(auditorium).on(concert.auditoriumId.eq(auditorium.id))
        .join(image).on(concert.id.eq(image.concertId))
        .fetch();
  }
}
