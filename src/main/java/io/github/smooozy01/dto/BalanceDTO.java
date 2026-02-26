package io.github.smooozy01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceDTO implements Serializable {
    
    private int id;
    private float balance;
    
    public BalanceDTO(float balance) {
        this.balance = balance;
    }
    
}
