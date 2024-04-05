package com.laydowncoding.tickitecking.domain.image.controller;

import com.laydowncoding.tickitecking.domain.image.dto.request.ImageRequestDto;
import com.laydowncoding.tickitecking.domain.image.service.ImageService;
import com.laydowncoding.tickitecking.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

  private final ImageService imageService;

  @PostMapping("/upload")
  @Secured({"ROLE_COMPANY_USER", "ROLE_ADMIN"})
  public ResponseEntity<CommonResponse<Void>> uploadFile(
      @ModelAttribute ImageRequestDto imageRequest
  ) {
    imageService.upload(imageRequest.getFile(), imageRequest.getConcertId());
    return CommonResponse.ok(null);
  }
}
