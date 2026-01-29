package io.github.smooozy01.facade;

import io.github.smooozy01.dto.CarDTO;
import io.github.smooozy01.dto.ClientDTO;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.model.Car;
import io.github.smooozy01.service.CarService;
import io.github.smooozy01.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ClientCarFacade {
    
    private final CarService carService;
    private final ClientService clientService;

    public ClientCarFacade(ClientService clientService, CarService carService) {
        this.clientService = clientService;
        this.carService = carService;
    }


    public List<CarDTO> getCars(String name) {
        return carService.getCars(name);
    }

    public CarDTO getCarByID(int id) {
        return carService.getCarByID(id);
    }

    public ResponseEntity<String> addCar(@Valid CarDTO carDTO) {
        
        carService.addCar(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    public ResponseEntity<String> updateCarByID(int id, @Valid CarDTO carDTO) {
        
        carService.updateCarByID(id, carDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }

    public ResponseEntity<String> deleteCarByID(int id) {
        return carService.deleteCarByID(id);
    }

    
    

    public List<ClientDTO> getClients(String clientName, String carName) {
        return clientService.searchClient(clientName, carName);
    }

    public ClientDTO getClientByID(int id) {
        return clientService.getClientByID(id);
    }

    public ResponseEntity<String> createClient(@Valid ClientDTO clientDTO) {

        if (clientDTO.getCarId() != null) {
            
            Optional<Car> car =  carService.getCarModelById(clientDTO.getCarId());
            
            if (car.isPresent()) {
                
                clientService.createClient(clientDTO, car.get());
                return ResponseEntity.status(HttpStatus.CREATED).body("Created!");
            }
            else {
                throw new DoesntExistException("car not found");
            }
        }
        
        clientService.createClient(clientDTO, null);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created!");
    }

    public ResponseEntity<String> updateClient(int id, ClientDTO clientDTO) {
        
        clientService.updateClient(id, clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }

    public ResponseEntity<String> deleteClient(int id) {
        
        return  clientService.deleteClient(id);
    }
}
