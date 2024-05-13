package com.laydowncoding.tickitecking.domain.image.repository;

import com.laydowncoding.tickitecking.domain.image.entity.Image;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByConcertId(Long id);
}
