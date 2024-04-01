package com.laydowncoding.tickitecking.domain.auditorium.dto.request;

import com.laydowncoding.tickitecking.domain.seat.dto.request.SeatRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriumRequestDto {

  @NotBlank
  private String name;

  @NotBlank
  private String address;

  @NotBlank
  @Pattern(regexp = "\\b(?:[1-9][0-9]?|100)\\b", message = "1부터 100까지의 값을 입력하세요.")
  private String maxColumn;

  @NotBlank
  @Pattern(regexp = "[A-Z]", message = "알파벳 대문자(A-Z)만 입력하세요.")
  private String maxRow;

  private String availability = "Y";

  private List<SeatRequestDto> seatList;
}

