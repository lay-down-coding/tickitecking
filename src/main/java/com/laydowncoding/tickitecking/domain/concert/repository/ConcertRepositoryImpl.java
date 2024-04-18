package com.laydowncoding.tickitecking.domain.concert.repository;

import static com.laydowncoding.tickitecking.domain.auditorium.entity.QAuditorium.auditorium;
import static com.laydowncoding.tickitecking.domain.concert.entitiy.QConcert.concert;
import static com.laydowncoding.tickitecking.domain.image.entity.QImage.image;
import static com.laydowncoding.tickitecking.domain.user.entity.QUser.user;

import com.laydowncoding.tickitecking.domain.concert.dto.AllConcertResponseDto;
import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ConcertRepositoryImpl extends QuerydslRepositorySupport implements
    ConcertRepositoryQuery {

  private final JPAQueryFactory queryFactory;

  public ConcertRepositoryImpl(JPAQueryFactory queryFactory) {
    super(Concert.class);
    this.queryFactory = queryFactory;
  }

  @Override
  public Page<AllConcertResponseDto> getAllConcerts(Pageable pageable) {
    JPQLQuery<AllConcertResponseDto> query = getQuerydsl().applyPagination(pageable,
        queryFactory.select(
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
            .orderBy(concert.id.desc()));

    List<AllConcertResponseDto> responses = query.fetch();
    Long totalCount = query.fetchCount();
    return new PageImpl<>(responses, pageable, totalCount);
  }
}
