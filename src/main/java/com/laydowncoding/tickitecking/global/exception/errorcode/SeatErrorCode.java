package com.laydowncoding.tickitecking.global.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SeatErrorCode {

  INVALID_HORIZONTAL(HttpStatus.BAD_REQUEST, "[ERROR] 잘못된 좌석 열 번호입니다.");

  private final HttpStatus httpStatus;
  private final String message;

  SeatErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
