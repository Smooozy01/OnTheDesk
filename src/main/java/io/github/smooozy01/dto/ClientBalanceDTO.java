package io.github.smooozy01.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Setter @Getter
public class ClientBalanceDTO implements Serializable {
    
    private ClientDTO client;
    private BalanceDTO balance;
                    
}
