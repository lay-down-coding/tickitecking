package com.laydowncoding.tickitecking.domain.event.repository;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {


  List<Event> findByStartAtIsAfter(LocalDate date);
}
