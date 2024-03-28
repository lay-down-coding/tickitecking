package com.laydowncoding.tickitecking.domain.auditorium.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laydowncoding.tickitecking.domain.auditorium.dto.request.AuditoriumRequestDto;
import com.laydowncoding.tickitecking.domain.auditorium.repository.AuditoriumRepository;
import com.laydowncoding.tickitecking.domain.auditorium.service.AuditoriumService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuditoriumController.class)
public class AuditoriumControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuditoriumRepository auditoriumRepository;

  @MockBean
  private AuditoriumService auditoriumService;

  @Autowired
  private ObjectMapper objectMapper;

  private String baseUrl = "/api/v1/auditoriums";

  @Nested
  @DisplayName("공연장 생성")
  class createAuditorium {

    @Test
    public void create_auditorium_success() throws Exception {
      mockMvc.perform(post(baseUrl)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(new AuditoriumRequestDto(
              "auditorium1",
              "address1",
              "C",
              "30"
          ))))
          .andExpect(status().isOk());
    }

    @Test
    public void create_auditorium_fail1() throws Exception {
      mockMvc.perform(post(baseUrl)
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(new AuditoriumRequestDto(
              "auditorium1",
              "address1",
              "a",
              "-1"
          ))))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$[0].message").exists())
          .andExpect(jsonPath("$[1].message").exists());
    }
  }
}
