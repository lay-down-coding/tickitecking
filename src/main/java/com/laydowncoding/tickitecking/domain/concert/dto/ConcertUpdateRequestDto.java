package com.laydowncoding.tickitecking.domain.concert.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertUpdateRequestDto {

    private String name;
    private String description;
    private LocalDateTime startTime;
    private Long auditoriumId;
}
