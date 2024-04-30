package com.laydowncoding.tickitecking.domain.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "seats")
@Getter
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String vertical;

    @Column
    private String horizontal;

    @Column(columnDefinition = "CHAR(1)")
    private String grade;

    @Column(nullable = false)
    private Long auditoriumId;

    @Column(nullable = false)
    private Long concertId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'AVAILABLE'")
    private SeatStatus seatStatus;

    @Builder
    public Seat(String vertical, String horizontal, String grade,
            Long auditoriumId, Long concertId, SeatStatus seatStatus) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.grade = grade;
        this.auditoriumId = auditoriumId;
        this.concertId = concertId;
        this.seatStatus = seatStatus;
    }

    public void update(String grade) {
        this.grade = grade;
    }

    public void toggleLock() {
        if (this.seatStatus.equals(SeatStatus.LOCKED)) {
            this.seatStatus = SeatStatus.AVAILABLE;
        } else {
            this.seatStatus = SeatStatus.LOCKED;
        }
    }

    public void reserve() {
        this.seatStatus = SeatStatus.RESERVED;
    }

    public void cancel() {
        this.seatStatus = SeatStatus.AVAILABLE;
    }

    public boolean isReservable() {
        return this.seatStatus.equals(SeatStatus.AVAILABLE);
    }
}
