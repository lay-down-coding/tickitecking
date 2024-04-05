package com.laydowncoding.tickitecking.global.exception.errorcode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ImageErrorCode {

  NO_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "[ERROR] 파일 확장자가 없습니다."),
  INVALID_FILE_EXTENSION(HttpStatus.BAD_REQUEST, "[ERROR] 잘못된 파일 확장자입니다."),
  EMPTY_FILE_EXCEPTION(HttpStatus.BAD_REQUEST, "[ERROR] 이미지 파일이 존재하지 않습니다."),
  PUT_OBJECT_EXCEPTION(HttpStatus.BAD_REQUEST, "[ERROR] 이미지 저장에 실패하였습니다.");

  private final HttpStatus httpStatus;
  private final String message;

  ImageErrorCode(HttpStatus httpStatus, String message) {
    this.httpStatus = httpStatus;
    this.message = message;
  }
}
