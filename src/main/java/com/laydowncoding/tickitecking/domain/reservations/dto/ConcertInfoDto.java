package com.laydowncoding.tickitecking.domain.reservations.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ConcertInfoDto {

    private Long concertId;
    private String concertName;
    private String concertDescription;
    private LocalDateTime concertStartTime;
    @Setter
    private Double concertGoldPrice;
    @Setter
    private Double concertSilverPrice;
    @Setter
    private Double concertBronzePrice;
    private Long auditoriumId;
    private String auditoriumName;
    private String auditoriumAddress;
    private String auditoriumMaxColumn;
    private String auditoriumMaxRow;
}
