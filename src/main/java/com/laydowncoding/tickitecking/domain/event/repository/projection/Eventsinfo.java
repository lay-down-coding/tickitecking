package com.laydowncoding.tickitecking.domain.event.repository.projection;

import java.time.LocalDate;

public interface Eventsinfo {
   String getName();
   String getCatagory();
   String getDescription();
   LocalDate getStartAt();
   Long getAuditoriumId();
}
