package com.laydowncoding.tickitecking.domain.auditorium.repository;

import com.laydowncoding.tickitecking.domain.auditorium.dto.response.AuditoriumResponseDto;
import com.laydowncoding.tickitecking.domain.auditorium.entity.QAuditorium;
import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import com.laydowncoding.tickitecking.domain.user.entity.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditoriumQueryRepositoryImpl implements AuditoriumQueryRepository {

  private final JPAQueryFactory query;


  @Override
  public List<AuditoriumResponseDto> findAllByCompanyUserId(Long companyUserId) {
    QAuditorium auditorium = QAuditorium.auditorium;
    QUser user = QUser.user;

    return query.select(
            Projections.constructor(
                AuditoriumResponseDto.class,
                auditorium.id,
                auditorium.name,
                auditorium.address,
                auditorium.maxColumn,
                auditorium.maxRow,
                Projections.constructor(
                    UserResponseDto.class,
                    user.id,
                    user.username,
                    user.email,
                    user.nickname
                )
            )
        )
        .from(auditorium)
        .leftJoin(user)
        .on(auditorium.companyUserId.eq(user.id))
        .where(auditorium.companyUserId.eq(companyUserId))
        .fetch();
  }

  @Override
  public List<AuditoriumResponseDto> findAll() {
    QAuditorium auditorium = QAuditorium.auditorium;
    QUser user = QUser.user;

    return query.select(
            Projections.constructor(
                AuditoriumResponseDto.class,
                auditorium.id,
                auditorium.name,
                auditorium.address,
                auditorium.maxColumn,
                auditorium.maxRow,
                Projections.constructor(
                    UserResponseDto.class,
                    user.id,
                    user.username,
                    user.email,
                    user.nickname
                )
            )
        )
        .from(auditorium)
        .leftJoin(user)
        .on(auditorium.companyUserId.eq(user.id))
        .fetch();
  }

  @Override
  public AuditoriumResponseDto findByAuditoriumId(Long auditoriumId) {
    QAuditorium auditorium = QAuditorium.auditorium;
    QUser user = QUser.user;

    return query.select(
            Projections.constructor(
                AuditoriumResponseDto.class,
                auditorium.id,
                auditorium.name,
                auditorium.address,
                auditorium.maxColumn,
                auditorium.maxRow,
                Projections.constructor(
                    UserResponseDto.class,
                    user.id,
                    user.username,
                    user.email,
                    user.nickname
                )
            )
        )
        .from(auditorium)
        .leftJoin(user)
        .on(auditorium.companyUserId.eq(user.id))
        .where(auditorium.id.eq(auditoriumId))
        .fetchOne();
  }
}
