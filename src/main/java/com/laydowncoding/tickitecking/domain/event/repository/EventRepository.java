package com.laydowncoding.tickitecking.domain.event.repository;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import com.laydowncoding.tickitecking.domain.event.repository.projection.Eventsinfo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {


  List<Eventsinfo> findByStartAtIsAfter(LocalDate date);

  List<Eventsinfo> findAllByIdAndUserid(Long id,Long Userid);
  Event findByIdAndUserid(Long id,Long Userid);
}
