package com.laydowncoding.tickitecking.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

  void upload(MultipartFile imageFile, Long concertId);
}
