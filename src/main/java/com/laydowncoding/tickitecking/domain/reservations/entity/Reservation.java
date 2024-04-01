package com.laydowncoding.tickitecking.domain.reservations.entity;

import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @Column(nullable = false)
    private char status;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Long seat_id;
}
