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
    
    public static List<ClientDTO> clientModelListToDTOList(List<Client> clients) {
        
        List<ClientDTO> clientDTOList = new ArrayList<>();
        
        for (Client client : clients) {
            clientDTOList.add(clientDTOFromModel(client));
        }
        
        return clientDTOList;
    }

    @Override
    public String toString() {
        return "name: " + name + ", CarId: " + carId + ", active: " + active;
    }
}
