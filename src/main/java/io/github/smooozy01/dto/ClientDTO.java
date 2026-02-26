package io.github.smooozy01.dto;

import io.github.smooozy01.model.Client;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO implements Serializable {
    
    private Integer id;
    
    @NotBlank
    private String name;
    
    private Integer carId;
    private String carName;
    
    private Boolean active;
    
    private float balance;

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.carId = client.getCar().getId();
        this.carName = client.getCar().getName();
        this.active = client.getActive();
        this.balance = client.getBalance();
    }

    public static ClientDTO clientDTOFromModel(Client client) {
        
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setName(client.getName());
        clientDTO.setActive(client.getActive());
        
        if (client.getCar() != null) {
            clientDTO.setCarId(client.getCar().getId());
            clientDTO.setCarName(client.getCar().getName());
        }
        
        return clientDTO;
    }
    
    public static List<ClientDTO> clientDTOListFromModelList(List<Client> clients) {
        
        List<ClientDTO> clientDTOList = new ArrayList<>();
        
        for (Client client : clients) {
            clientDTOList.add(clientDTOFromModel(client));
        }
        
        return clientDTOList;
    }

    public static List<BalanceDTO> balanceDTOListFromModelList(List<Client> all) {
        
        List<BalanceDTO> balanceDTOList = new ArrayList<>();
        
        for (Client client : all) {
            balanceDTOList.add(new BalanceDTO(client.getId(), client.getBalance()));
        }
        
        return balanceDTOList;
        
    }
    
    @Override
    public String toString() {
        return "name: " + name + ", CarId: " + carId + ", active: " + active;
    }
}
