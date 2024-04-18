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

  @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean locked;

  @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
  private boolean reserved;

  @Column(nullable = false)
  private Long auditoriumId;

  @Column(nullable = false)
  private Long concertId;

  @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
  private boolean isAvailable;

  public Seat(String vertical, String horizontal, String grade, Long auditoriumId) {
    this.vertical = vertical;
    this.horizontal = horizontal;
    this.grade = grade;
    this.auditoriumId = auditoriumId;
  }

  @Builder
  public Seat(String vertical, String horizontal, String grade,
      Long auditoriumId, Long concertId, boolean reserved, boolean locked) {
    this.vertical = vertical;
    this.horizontal = horizontal;
    this.grade = grade;
    this.auditoriumId = auditoriumId;
    this.concertId = concertId;
    this.reserved = reserved;
    this.locked = locked;
    this.isAvailable = true;
  }

  public void update(String grade) {
      this.grade = grade;
  }

  public void toggleLock() {
      this.locked = !locked;
      assignAvailability();
  }

  public void reserve() {
      this.reserved = true;
      assignAvailability();
  }

  public void cancel() {
      this.reserved = false;
      assignAvailability();
  }

  public boolean isReservable() {
      return !this.locked && !this.reserved;
  }

  private void assignAvailability() {
      this.isAvailable = !locked && !reserved;
  }
}
