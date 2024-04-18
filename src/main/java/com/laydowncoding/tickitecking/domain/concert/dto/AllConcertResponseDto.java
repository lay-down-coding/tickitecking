package com.laydowncoding.tickitecking.domain.concert.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(Include.NON_NULL)
public class AllConcertResponseDto {

    private Long id;
    private String name;
    private String imagePath;
    private LocalDateTime startTime;
    private String writer;
    private String auditoriumName;
}
