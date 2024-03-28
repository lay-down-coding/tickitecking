package com.laydowncoding.tickitecking.domain.event.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Event {

  @Id
  private Long event_id;

  public Long getEvent_id() {
    return event_id;
  }
}
