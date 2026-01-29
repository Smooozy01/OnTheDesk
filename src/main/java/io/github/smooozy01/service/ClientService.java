package io.github.smooozy01.service;

import io.github.smooozy01.dto.ClientDTO;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.model.Car;
import io.github.smooozy01.model.Client;
import io.github.smooozy01.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    
    
    @Cacheable(value = "client_search", key = "{#clientName, #carName}")
    public List<ClientDTO> searchClient(String clientName, String carName) {
        
        if (clientName == null && carName == null) {
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findAll());
        }
        
        else if (clientName != null && carName == null) {
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findByNameContainingIgnoreCase(clientName));
        }
        
        else if (clientName == null && carName != null) {
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findByCarNameContainingIgnoreCase(carName));
        }
        
        else{
            return ClientDTO.clientModelListToDTOList(
                    clientRepository.findByNameContainingIgnoreCaseAndCarNameContainingIgnoreCase(clientName, carName));
        }

    }   
    
    
    @Cacheable(value = "clients", key = "#id")
    public ClientDTO getClientByID(int id) {
        Optional<Client> client = clientRepository.findById(id);
        
        if (client.isPresent())
            return ClientDTO.clientDTOFromModel(client.get());
        
        else 
            return null;
    }


    @Caching(
            put = { @CachePut(value = "clients", key = "#result.id") },
            evict = { @CacheEvict(value = "client_search", allEntries = true) }
    )
    public ClientDTO createClient(@Valid ClientDTO clientDTO, Car car) {

        Client client = new Client(
                clientDTO.getName(),
                car,
                clientDTO.getActive());
        
        clientRepository.save(client);
        
        return ClientDTO.clientDTOFromModel(client);
    }
    
    
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "clients", key = "#id"),
            @CacheEvict(value = "client_search", allEntries = true)
    })
    public ClientDTO updateClient(int id, ClientDTO clientDTO) {

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
                
                Optional<Car> car = carService.getCarModelById(clientDTO.getCarId());  //TODO - Calling service or facade?
                if (car.isPresent()) {
                    client.setCar(car.get());
                } 
                else{
                    throw new DoesntExistException("No car with such ID");
                }
                
            }

            return ClientDTO.clientDTOFromModel(client);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoesntExistException("No record with such ID");
        }

    }
    
    
    @Caching(evict = {
            @CacheEvict(value = "clients", key = "#id"),
            @CacheEvict(value = "client_search", allEntries = true)
    })
    public ResponseEntity<String> deleteClient(int id) {
        
        clientRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted!");
    }
    
}
