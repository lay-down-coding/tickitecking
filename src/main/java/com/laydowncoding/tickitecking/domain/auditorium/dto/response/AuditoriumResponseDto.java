package com.laydowncoding.tickitecking.domain.auditorium.dto.response;

import com.laydowncoding.tickitecking.domain.user.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriumResponseDto {

    private Long id;

    private String name;

    private String address;

    private String maxColumn;

    private String maxRow;

    private UserResponseDto companyUser;
}
