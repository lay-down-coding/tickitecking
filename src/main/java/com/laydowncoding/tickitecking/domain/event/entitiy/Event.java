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

  @Column
  private String name;
  @Column
  private String description;
  @Column
  private String catagory;
  @Column
  private LocalDate startAt;
  @Column
  private Long auditoriumId;
  @Column
  private Long userid;

  public Event(EventRequestDto dto, Long userid) {
    this.name = dto.getName();
    this.catagory = dto.getCatagory();
    this.description = dto.getDescription();
    this.startAt = dto.getStart_date();
    this.auditoriumId = dto.getAuditorium_id();
    this.userid =userid;
  }

  public Event() {

  }
  //
  @Transactional
  public void update(EventRequestDto dto){
    this.name = dto.getName();
    this.catagory = dto.getCatagory();
    this.description = getDescription();
    this.startAt = dto.getStart_date();
    this.auditoriumId = dto.getAuditorium_id();
  }
}
