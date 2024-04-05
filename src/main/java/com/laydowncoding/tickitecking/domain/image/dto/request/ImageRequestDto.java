package com.laydowncoding.tickitecking.domain.image.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {

  private MultipartFile file;
  private Long concertId;
}
