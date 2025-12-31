package io.github.smooozy01.repository;

import io.github.smooozy01.model.RefuelModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface RefuelRepository extends JpaRepository<RefuelModel, Integer> {

    boolean existsByDateAndMileage(LocalDate date, Integer mileage);
    Optional<RefuelModel> findTopByOrderByIdDesc();
    
}
