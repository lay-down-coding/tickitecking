package com.laydowncoding.tickitecking.domain.admin.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminConcertResponseDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime startTime;
}
