package io.github.smooozy01.repository;

import io.github.smooozy01.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Integer> {

    List<Car> findByNameContainingIgnoreCase(String name);
}
