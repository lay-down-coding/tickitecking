package com.laydowncoding.tickitecking.domain.concert.dto;

import jakarta.persistence.Column;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConcertCreateRequestDto {

    private String name;
    private String description;
    private LocalDateTime startTime;
    private Long auditoriumId;
}
