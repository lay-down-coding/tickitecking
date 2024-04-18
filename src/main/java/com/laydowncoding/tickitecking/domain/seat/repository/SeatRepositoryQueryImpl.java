package com.laydowncoding.tickitecking.domain.seat.repository;

import com.laydowncoding.tickitecking.domain.seat.entity.Seat;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class SeatRepositoryQueryImpl implements SeatRepositoryQuery {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllSeat(List<Seat> seatList) {
        String sql =
            "INSERT INTO seats (auditorium_id, concert_id, grade, horizontal, vertical)"
                + "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Seat seat = seatList.get(i);

                ps.setLong(1, seat.getAuditoriumId());
                ps.setLong(2, seat.getConcertId());
                ps.setString(3, seat.getGrade());
                ps.setString(4, seat.getHorizontal());
                ps.setString(5, seat.getVertical());
            }

            @Override
            public int getBatchSize() {
                return seatList.size();
            }
        });
    }

    @Override
    public void updateAllSeat(List<Seat> seatList) {
        String sql = "UPDATE seats SET grade = ? WHERE concert_id = ? AND horizontal = ? AND vertical = ?";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Seat seat = seatList.get(i);
                ps.setString(1, seat.getGrade());
                ps.setLong(2, seat.getConcertId());
                ps.setString(3, seat.getHorizontal());
                ps.setString(4, seat.getVertical());
            }

            @Override
            public int getBatchSize() {
                return seatList.size();
            }
        });
    }
}
