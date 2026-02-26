package io.github.smooozy01.dto;

import io.github.smooozy01.model.Transaction;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private String comment;
    
    @NotNull
    private Integer clientId;
    private String clientName;
    
    @NotNull
    private LocalDateTime date;
    
    @NotNull
    private Float amount;

    
    public static TransactionDTO DTOFromModel(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getComment(),
                transaction.getClient().getId(),
                transaction.getClient().getName(),
                transaction.getDate(),
                transaction.getAmount()
        );
    }

    public static List<TransactionDTO> DTOListFromModelList(List<Transaction> transactions) {
        
        List<TransactionDTO> dtos = new ArrayList<>();
        
        for (Transaction transaction : transactions) {
            dtos.add(DTOFromModel(transaction));
        }
        
        return dtos;
    }
    
    public static Transaction ModelFromDTO(TransactionDTO transactionDTO) {
        return new Transaction(
                
        );
    }
    
}
