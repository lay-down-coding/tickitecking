package com.laydowncoding.tickitecking.domain.concert.entitiy;

import com.laydowncoding.tickitecking.domain.concert.dto.ConcertUpdateRequestDto;
import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Table(name = "concerts")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE concerts SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction(value = "deleted_at is NULL")
public class Concert extends Timestamp {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @Column(nullable = false)
  private LocalDateTime startTime;

  @Column(nullable = false)
  private Long companyUserId;

  @Column(nullable = false)
  private Long auditoriumId;

  @Builder
  public Concert(String name, String description, LocalDateTime startTime, Long companyUserId,
      Long auditoriumId) {
    this.name = name;
    this.description = description;
    this.startTime = startTime;
    this.companyUserId = companyUserId;
    this.auditoriumId = auditoriumId;
  }

  public void update(ConcertUpdateRequestDto requestDto) {
    this.name = requestDto.getName();
    this.description = requestDto.getDescription();
    this.startTime = requestDto.getStartTime();
    this.auditoriumId = requestDto.getAuditoriumId();
  }
}
