package com.laydowncoding.tickitecking.domain.event.entitiy;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.global.entity.Timestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.transaction.annotation.Transactional;

@Entity
@DynamicUpdate
@Getter
@Table(name = "events")
public class Event extends Timestamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;
  @Column()
  private String description;
  @Column(nullable = false)
  private String category;
  @Column(nullable = false)
  private LocalDate startAt;
  @Column(nullable = false)
  private Long auditoriumId;
  @Column(nullable = false)
  private Long userid;

  public Event(EventRequestDto dto, Long userid) {
    this.name = dto.getName();
    this.category = dto.getCategory();
    this.description = dto.getDescription();
    this.startAt = dto.getStart_date();
    this.auditoriumId = dto.getAuditorium_id();
    this.userid =userid;
  }
  public Event() {
  }

  public void update(EventRequestDto eventRequestDto){
    this.name = eventRequestDto.getName();
    this.category = eventRequestDto.getCategory();
    this.description = eventRequestDto.getDescription();
    this.startAt = eventRequestDto.getStart_date();
    this.auditoriumId = eventRequestDto.getAuditorium_id();
  }
  public void setDelete() {
    super.deletedAt = LocalDateTime.now();
  }
}
