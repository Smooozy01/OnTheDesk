package io.github.smooozy01.repository;

import io.github.smooozy01.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Integer>,
                                          JpaSpecificationExecutor<Client> {
    
    List<Client> findByName(String name);
    List<Client> findByCarName(String carName);
    List<Client> findByNameAndCarName(String name,String carName);
}
