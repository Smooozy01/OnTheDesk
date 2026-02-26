package io.github.smooozy01.repository;

import io.github.smooozy01.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface ClientRepository extends JpaRepository<Client, Integer>,
                                          JpaSpecificationExecutor<Client> {

    Page<Client> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Client> findByCarNameContainingIgnoreCase(String carName, Pageable pageable);

    Page<Client> findByNameContainingIgnoreCaseAndCarNameContainingIgnoreCase(String name, String carName, Pageable pageable);
}
