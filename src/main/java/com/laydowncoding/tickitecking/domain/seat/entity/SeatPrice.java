package com.laydowncoding.tickitecking.domain.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat_prices")
@Getter
@NoArgsConstructor
public class SeatPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, columnDefinition = "CHAR(1)")
    private String grade;

    @Column(nullable = false)
    private Long concertId;

    @Builder
    public SeatPrice(Double price, String grade, Long concertId) {
        this.price = price;
        this.grade = grade;
        this.concertId = concertId;
    }

    public void update(String grade, Double price) {
        this.grade = grade;
        this.price = price;
    }
}
