package com.laydowncoding.tickitecking.domain.user.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SignupRequestDtoTest {

    @DisplayName("생성 성공")
    @Test
    void create_success() {
        //given
        SignupRequestDto requestDto = SignupRequestDto.builder()
            .username("test_username")
            .password("pass@i344")
            .email("test@email.com")
            .nickname("johny")
            .build();

        //when
        Set<ConstraintViolation<SignupRequestDto>> validate = validate(requestDto);

        //then
        assertThat(validate).isEmpty();
    }

    @DisplayName("생성 실패")
    @Test
    void create_fail() {
        //given
        SignupRequestDto requestDto = SignupRequestDto.builder()
            .username("")
            .password("fail")
            .email("test")
            .nickname("johny")
            .build();

        //when
        Set<ConstraintViolation<SignupRequestDto>> validate = validate(requestDto);

        //then
        assertThat(validate).hasSize(3);
    }

    private Set<ConstraintViolation<SignupRequestDto>> validate(SignupRequestDto requestDto) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(requestDto);
    }
}
