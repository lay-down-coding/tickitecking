package com.laydowncoding.tickitecking.eventTests.serviceTest;

import static org.mockito.BDDMockito.given;

import com.laydowncoding.tickitecking.domain.event.dto.EventRequestDto;
import com.laydowncoding.tickitecking.domain.event.entitiy.Event;
import com.laydowncoding.tickitecking.domain.event.repository.EventRepository;
import com.laydowncoding.tickitecking.domain.event.service.EventService;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
  @Mock
  EventRepository eventRepository;

  @Test
  @DisplayName("이벤트 생성")
  void eventCreate(){
    //givem
    EventService eventService = new EventService(eventRepository);
    EventRequestDto dto = new EventRequestDto("String1","String1","String1", LocalDate.now(),1l);
    //when
    given(eventRepository.save(new Event(dto,1L))).willReturn(new Event(dto,1l));
    eventService.createEvent(1l,dto);

    //then

  }

  @Test
  @DisplayName("이벤트 수정")
  void eventupdate(){

  }

  @Test
  @DisplayName("이벤트 삭제(신청)")
  void eventdelete(){

  }

}
