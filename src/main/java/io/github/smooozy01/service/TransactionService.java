package io.github.smooozy01.service;

import io.github.smooozy01.dto.TransactionDTO;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.model.Client;
import io.github.smooozy01.model.Transaction;
import io.github.smooozy01.repository.TransactionRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    private final ClientService clientService;
    
    public TransactionService(TransactionRepository transactionRepository, ClientService clientService) {
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
    }
    
    
    @Cacheable(value = "transactions_all")
    public List<TransactionDTO> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return TransactionDTO.DTOListFromModelList(transactions);
    }
    
    @Cacheable(value = "transactions", key = "#id")
    public TransactionDTO getTransactionById(Integer id) {
        
        return transactionRepository.findById(id)
                .map(TransactionDTO::DTOFromModel)
                .orElse(null);
        
    }
    
    
    @Transactional
    @Caching(
            put = {@CachePut(value = "transactions", key = "#result.id")},
            evict = {@CacheEvict(value = "transactions_all", allEntries = true), 
                     @CacheEvict(value = "balances", allEntries = true)}
    )
    public TransactionDTO createTransaction(TransactionDTO transaction, Client client) {
        
        Transaction newTransaction = new Transaction(
                transaction.getComment(),
                client,
                transaction.getDate(),
                transaction.getAmount()
        );
        
        Transaction saved = transactionRepository.save(newTransaction);
        return TransactionDTO.DTOFromModel(saved);
    }
    
    
    @Transactional
    @Caching(
            put = {@CachePut(value = "transactions", key = "#id")},
            evict = {@CacheEvict(value = "transactions_all", allEntries = true)}
    )
    public TransactionDTO updateTransaction(Integer id, TransactionDTO transactionDTO) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new DoesntExistException("No record with such id"));


        if (transactionDTO.getComment() != null) {
            transaction.setComment(transactionDTO.getComment());
        }

        if (transactionDTO.getDate() != null) {
            transaction.setDate(transactionDTO.getDate());
        }

        if (transactionDTO.getAmount() != null) {
            transaction.setAmount(transactionDTO.getAmount());
        }

        if (transactionDTO.getClientId() != null) {

            Optional<Client> client = clientService.getClientModelById(transactionDTO.getClientId());
            if (client.isPresent()) {
                transaction.setClient(client.get());
            }
            else{
                throw new DoesntExistException("No Client with such id");
            }
            
        }

        return TransactionDTO.DTOFromModel(transactionRepository.save(transaction));

    }
    
    
    @Caching(evict = {
            @CacheEvict(value = "transactions", key = "#id"),
            @CacheEvict(value = "transactions_all", allEntries = true)
    })
    public ResponseEntity<String> deleteTransaction(Integer id) {
        transactionRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Transaction has been deleted");
    }
    
}
