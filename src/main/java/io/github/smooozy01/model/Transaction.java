package io.github.smooozy01.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String comment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @NotNull
    private Client client;
    
    @NotNull
    private LocalDateTime date;
    
    @NotNull
    private Float amount;
    
    
    public Transaction(String comment, Client client, LocalDateTime date,  Float amount) {
        this.comment = comment;
        this.client = client;
        this.date = date;
        this.amount = amount;
    }
    
}
