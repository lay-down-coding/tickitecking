package com.laydowncoding.tickitecking.domain.seat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DialectOverride.ColumnDefault;

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

  @Column(columnDefinition = "CHAR(1) default 'Y'")
  private String availability;

  @Column(columnDefinition = "CHAR(1)")
  private String grade;

  @Column(nullable = false)
  private Long auditoriumId;

  public Seat(String vertical, String horizontal, String availability, String grade, Long auditoriumId) {
    this.vertical = vertical;
    this.horizontal = horizontal;
    this.availability = availability;
    this.grade = grade;
    this.auditoriumId = auditoriumId;
  }
}
