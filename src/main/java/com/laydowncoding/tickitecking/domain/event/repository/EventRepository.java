package com.laydowncoding.tickitecking.domain.event.repository;

import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event,Long> {

}
