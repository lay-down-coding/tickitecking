package com.laydowncoding.tickitecking.domain.admin.dto.response;

import com.laydowncoding.tickitecking.domain.seat.dto.response.SeatResponseDto;
import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationResponseDto {

  private Long id;
  private String status;
  private UserResponseDto reservedUser;
  private AdminConcertResponseDto concert;
  private SeatResponseDto seat;
  private LocalDateTime reservedTime;
  private LocalDateTime canceledTime;
}
