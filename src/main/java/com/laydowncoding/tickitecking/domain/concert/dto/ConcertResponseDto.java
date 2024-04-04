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
public class ConcertResponseDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
    private Long companyUserId;
    private Long auditoriumId;
    private String auditoriumName;
    private String auditoriumAddress;
    private String auditoriumMaxColumn;
    private String auditoriumMaxRow;
    private double goldPrice;
    private double silverPrice;
    private double bronzePrice;
}
