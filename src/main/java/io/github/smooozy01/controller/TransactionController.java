package io.github.smooozy01.controller;

import io.github.smooozy01.dto.TransactionDTO;
import io.github.smooozy01.facade.TransactionClientFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {
    
    private final TransactionClientFacade  transactionClientFacade;
    public TransactionController(TransactionClientFacade transactionClientFacade) {
        this.transactionClientFacade = transactionClientFacade;
    }
    
    
    @GetMapping
    public List<TransactionDTO> getTransactions(){
        return transactionClientFacade.getTransactions();
    }
    
    
    @GetMapping("{id}")
    public TransactionDTO getTransactionById(@PathVariable Integer id){
        return transactionClientFacade.getTransactionById(id);
    }
    
    
    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody @Valid TransactionDTO transactionDTO){
        return transactionClientFacade.createTransaction(transactionDTO);
    }
    
    
    @PutMapping("{id}")
    public ResponseEntity<String> updateTransaction(@PathVariable Integer id, 
                                                    @RequestBody TransactionDTO transactionDTO){
        
        return transactionClientFacade.updateTransaction(id, transactionDTO);
    }
    
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Integer id){
        return transactionClientFacade.deleteTransaction(id);
    }
    
}
