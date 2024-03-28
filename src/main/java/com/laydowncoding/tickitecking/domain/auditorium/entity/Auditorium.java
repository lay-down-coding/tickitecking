package com.laydowncoding.tickitecking.domain.auditorium.entity;

import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "auditoriums")
@NoArgsConstructor
@SQLRestriction(value = "deleted_at is NULL")
public class Auditorium extends Timestamp {

  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String name;

  @Column
  private String address;

  @Column
  private String max_column;

  @Column
  private String max_row;

  @Column(nullable = false)
  private Long company_user_id;
}
