package io.github.smooozy01.repository;

import io.github.smooozy01.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Integer>,
                                          JpaSpecificationExecutor<Client> {
    
    // Search by name
    List<Client> findByNameContainingIgnoreCase(String name);
    
    // Search by car name
    List<Client> findByCarNameContainingIgnoreCase(String carName);
    
    // Search by both
    List<Client> findByNameContainingIgnoreCaseAndCarNameContainingIgnoreCase(String name, String carName);
}
