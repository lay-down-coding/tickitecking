package com.laydowncoding.tickitecking.domain.image.service;

import static com.laydowncoding.tickitecking.global.exception.errorcode.ConcertErrorCode.NOT_FOUND_CONCERT;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ImageErrorCode.EMPTY_FILE_EXCEPTION;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ImageErrorCode.NO_FILE_EXTENSION;
import static com.laydowncoding.tickitecking.global.exception.errorcode.ImageErrorCode.PUT_OBJECT_EXCEPTION;

import com.laydowncoding.tickitecking.domain.concert.entitiy.Concert;
import com.laydowncoding.tickitecking.domain.concert.repository.ConcertRepository;
import com.laydowncoding.tickitecking.domain.image.entity.Image;
import com.laydowncoding.tickitecking.domain.image.repository.ImageRepository;
import com.laydowncoding.tickitecking.global.exception.CustomRuntimeException;
import com.laydowncoding.tickitecking.global.exception.errorcode.ImageErrorCode;
import io.awspring.cloud.s3.S3Exception;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

  @Value("${upload.path}")
  private String uploadPath;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  private final S3Client s3Client;

  private final ImageRepository imageRepository;

  private final ConcertRepository concertRepository;

  @Override
  @Transactional
  public void upload(MultipartFile imageFile, Long concertId) {
    if (imageFile.isEmpty() || Objects.isNull(imageFile.getOriginalFilename())) {
      throw new CustomRuntimeException(EMPTY_FILE_EXCEPTION.getMessage());
    }

    Concert concert = concertRepository.findById(concertId).orElseThrow(
        () -> new CustomRuntimeException(NOT_FOUND_CONCERT.getMessage())
    );

    Image image = getImage(imageFile, concert.getId());
    imageRepository.save(image);
  }

  private Image getImage(MultipartFile imageFile, Long concertId) {
    try {
      String originalFileName = imageFile.getOriginalFilename();
      String extension = validateImageFileExtention(originalFileName);
      String saveFileName = createSaveFileName(originalFileName);

      PutObjectRequest putObjectRequest = PutObjectRequest.builder()
          .bucket(bucket)
          .key(uploadPath + saveFileName)
          .contentType(String.valueOf(MediaType.MULTIPART_FORM_DATA))
          .build();
      PutObjectResponse response = s3Client
          .putObject(putObjectRequest, RequestBody.fromBytes(imageFile.getBytes()));

      String filePath = getFilePath(uploadPath + saveFileName);
      String contentType = imageFile.getContentType();

      if (response.sdkHttpResponse().statusText().orElse("FAIL").equals("OK")) {
        Image image = Image.builder()
            .originalFileName(originalFileName)
            .saveFileName(saveFileName)
            .contentType(contentType)
            .filePath(filePath)
            .concertId(concertId)
            .build();
        return image;
      } else {
        throw new CustomRuntimeException(PUT_OBJECT_EXCEPTION.getMessage());
      }
    } catch (IOException | S3Exception | IllegalStateException ie) {
      throw new RuntimeException(ie.getMessage());
    }
  }

  private String getFilePath(String filePath) {
    return s3Client.utilities().getUrl(
        GetUrlRequest.builder().bucket(bucket)
            .key(filePath).build()
    ).toExternalForm();
  }

  private String validateImageFileExtention(String originalFilename) {
    String extension = extractExt(originalFilename);
    List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

    if (!allowedExtentionList.contains(extension)) {
      throw new CustomRuntimeException(ImageErrorCode.INVALID_FILE_EXTENSION.getMessage());
    }

    return extension;
  }

  private String createSaveFileName(String originalFilename) {
    String ext = extractExt(originalFilename);
    String uuid = UUID.randomUUID().toString();
    return uuid + "." + ext;
  }

  private String extractExt(String originalFilename) {
    int lastDotIndex = originalFilename.lastIndexOf(".");
    if (lastDotIndex == -1) {
      throw new CustomRuntimeException(NO_FILE_EXTENSION.getMessage());
    }

    return originalFilename.substring(lastDotIndex + 1).toLowerCase();
  }
}
