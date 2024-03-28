package com.laydowncoding.tickitecking.domain.auditorium.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AuditoriumRepositoryTest {

  @Autowired
  private AuditoriumRepository auditoriumRepository;


}
