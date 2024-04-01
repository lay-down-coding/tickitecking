package com.laydowncoding.tickitecking.domain.reservations.entity;

import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "reservations")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE reservations SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class Reservation extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "CHAR(1) default 'Y'")
    private String status;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long concertId;

    @Column(nullable = false)
    private Long seatId;

    @Builder
    public Reservation(String status, Long userId, Long concertId, Long seatId) {
        this.status = status;
        this.userId = userId;
        this.concertId = concertId;
        this.seatId = seatId;
    }
}
