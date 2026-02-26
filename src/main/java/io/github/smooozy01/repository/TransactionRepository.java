package io.github.smooozy01.repository;

import io.github.smooozy01.model.Transaction;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    
    @Override
    @EntityGraph(attributePaths = {"client"})
    List<Transaction> findAll();
}
