package com.laydowncoding.tickitecking.domain.seat.repository;

import static com.laydowncoding.tickitecking.domain.seat.entity.QSeat.seat;

import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class SeatRepositoryQueryImpl implements SeatRepositoryQuery {

  private final JPAQueryFactory jpaQueryFactory;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Boolean isReservable(Long concertId, String horizontal, String vertical) {
    String reserved = jpaQueryFactory.select(seat.reserved)
        .from(seat)
        .where(reservableCondition(concertId, horizontal, vertical))
        .fetchFirst();

    return reserved.equals("N");
  }

  @Override
  public void saveAllSeat(List<Seat> seatList) {
    String sql =
        "INSERT INTO seats (auditorium_id, availability, concert_id, grade, horizontal, reserved, vertical)"
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
    jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement ps, int i) throws SQLException {
        Seat seat = seatList.get(i);

        ps.setLong(1, seat.getAuditoriumId());
        ps.setString(2, seat.getAvailability());
        ps.setLong(3, seat.getConcertId());
        ps.setString(4, seat.getGrade());
        ps.setString(5, seat.getHorizontal());
        ps.setString(6, seat.getReserved());
        ps.setString(7, seat.getVertical());
      }

      @Override
      public int getBatchSize() {
        return 100;
      }

    });
  }

  private BooleanExpression reservableCondition(Long concertId, String horizontal,
      String vertical) {
    return seat.concertId.eq(concertId)
        .and(seat.vertical.eq(vertical))
        .and(seat.horizontal.eq(horizontal));
  }
}
