package com.laydowncoding.tickitecking.domain.concert.repository;

import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {

}
