package com.laydowncoding.tickitecking.domain.event.repository;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import com.laydowncoding.tickitecking.domain.event.repository.projection.EventsInfoWithOutUserId;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
  List<EventsInfoWithOutUserId> findByStartAtIsAfter(LocalDate date);
  Event findByIdAndUserid(Long id,Long UserId);
  List<EventsInfoWithOutUserId> findAllByUserid(Long id);
}
