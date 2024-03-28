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

@Entity
@DynamicUpdate
@Getter
@Table(name = "events")
public class Event extends Timestamp {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;
  @Column
  private String description;
  @Column
  private String catagory;
  @Column
  private LocalDate startAt;
  @Column
  private Long auditorium_id;
  @Column
  private Long user_id;

  public Event(EventRequestDto dto, Long userid) {
    this.name = dto.getName();
    this.catagory = dto.getCatagory();
    this.description = getDescription();
    this.startAt = dto.getStart_date();
    this.auditorium_id = dto.getAuditorium_id();
    this.user_id =userid;
  }

  public Event() {

  }
}
