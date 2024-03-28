package com.laydowncoding.tickitecking.domain.event.repository.projection;

import java.time.LocalDate;

public interface eventsinfo {
   String getName();
   String getCatagory();
   String getDescription();
   LocalDate getStart_date();
   Long getAuditorium_id();
}
