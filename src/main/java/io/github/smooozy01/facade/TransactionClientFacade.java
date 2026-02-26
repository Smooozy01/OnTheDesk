package io.github.smooozy01.facade;

import io.github.smooozy01.dto.TransactionDTO;
import io.github.smooozy01.service.ClientService;
import io.github.smooozy01.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionClientFacade {
    
    private final TransactionService transactionService;
    private final ClientService clientService;

    public TransactionClientFacade(TransactionService transactionService, ClientService clientService) {
        this.transactionService = transactionService;
        this.clientService = clientService;
    }


    public List<TransactionDTO> getTransactions() {
        return transactionService.getTransactions();
    }

    public TransactionDTO getTransactionById(Integer id) {
        return transactionService.getTransactionById(id);
    }
    
    public ResponseEntity<String> createTransaction(TransactionDTO transactionDTO) {
        
        if (transactionDTO.getClientId() == null || 
                transactionDTO.getDate() == null || 
                transactionDTO.getAmount() == null) {
            
            return ResponseEntity.badRequest().body("Error creating transaction");
        }
        
        if (clientService.getClientByID(transactionDTO.getClientId()) == null) {
            return ResponseEntity.badRequest().body("Error creating transaction, Client not found");
        }
        
        transactionService.createTransaction(transactionDTO, clientService.getClientModelById(transactionDTO.getClientId()).get());
        clientService.changeBalance(transactionDTO.getClientId(), transactionDTO.getAmount());
        return ResponseEntity.status(HttpStatus.CREATED).body("Transaction created");
    }
    
    public ResponseEntity<String> updateTransaction(Integer id, TransactionDTO transactionDTO) {

        if (transactionDTO.getClientId() == null ||
                transactionDTO.getDate() == null ||
                transactionDTO.getAmount() == null) {

            return ResponseEntity.badRequest().body("Error creating transaction");
        }
        
        if (transactionService.getTransactionById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found");
        }

        if (clientService.getClientByID(transactionDTO.getClientId()) == null) {
            return ResponseEntity.badRequest().body("Error creating transaction, Client not found");
        }
        
        Float oldAmount = transactionService.getTransactionById(id).getAmount();
        clientService.changeBalance(transactionDTO.getClientId(),  transactionDTO.getAmount() -  oldAmount);
        
        transactionService.updateTransaction(id, transactionDTO);
        
        return ResponseEntity.status(HttpStatus.OK).body("Transaction updated");
        
    }
    
    public ResponseEntity<String> deleteTransaction(Integer id) { 
        
        TransactionDTO transaction = transactionService.getTransactionById(id);
        if (transaction != null) clientService.changeBalance(id, transaction.getAmount() * -1);
        
        return transactionService.deleteTransaction(id);
    }
    
}
    