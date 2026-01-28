package io.github.smooozy01.service;

import io.github.smooozy01.dto.ClientDTO;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.model.Car;
import io.github.smooozy01.model.Client;
import io.github.smooozy01.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    
    private final ClientRepository clientRepository;
    private final CarService carService;

    public ClientService(ClientRepository clientRepository, CarService carService) { this.clientRepository = clientRepository;
        this.carService = carService;
    }
    
    
    public List<ClientDTO> searchClient(String clientName, String carName) {
        
        if (clientName == null && carName == null) {
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findAll());
        }
        
        else if (clientName != null && carName == null) {
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findByName(clientName));
        }
        
        else if (clientName == null && carName != null) {
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findByCarName(carName));
        }
        
        else{
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findByNameAndCarName(clientName, carName));
        }

    }   
    
    
    public ClientDTO getClientByID(int id) {
        Optional<Client> client = clientRepository.findById(id);
        
        if (client.isPresent())
            return ClientDTO.clientDTOFromModel(client.get());
        
        else 
            return null;
    }
    

    public ResponseEntity<String> createClient(@Valid ClientDTO clientDTO, Car car) {

        clientRepository.save(
                new Client(
                clientDTO.getName(),
                car, 
                clientDTO.getActive()
        ));
        
//        if (car != null) {
//            clientRepository.save(
//                    new Client(clientDTO.getName(), car));
//        }
//        else {
//            clientRepository.save(
//                    new Client(clientDTO.getName(), null));
//        }
//        
        return ResponseEntity.status(HttpStatus.CREATED).body("Created!");
    }
    
    
    @Transactional
    public ResponseEntity<String> updateClient(int id, ClientDTO clientDTO) {

        try {
            Client client = clientRepository.findById(id).orElseThrow(() -> 
                    new DoesntExistException("No record with such ID"));
            
            
            if (clientDTO.getName() != null) {
                client.setName(clientDTO.getName());
            }
            
            if (clientDTO.getActive() != null) {
                client.setActive(clientDTO.getActive());
            }
            
            if (clientDTO.getCarId() != null) {
                
                Optional<Car> car = carService.getCarModelById(clientDTO.getCarId());
                if (car.isPresent()) {
                    client.setCar(car.get());
                } 
                else{
                    throw new DoesntExistException("No car with such ID");
                }
                
            }

            return ResponseEntity.status(HttpStatus.OK).body("Updated");

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoesntExistException("No record with such ID");
        }

    }
    
    
    public ResponseEntity<String> deleteClient(int id) {
        
        clientRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted!");
    }
    
}
