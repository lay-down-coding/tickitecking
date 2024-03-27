package com.laydowncoding.tickitecking.domain.auditoriums.respository;

import com.laydowncoding.tickitecking.domain.auditoriums.entity.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {

}
