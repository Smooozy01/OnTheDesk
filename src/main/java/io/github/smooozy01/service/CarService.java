package io.github.smooozy01.service;

import io.github.smooozy01.dto.CarDTO;
import io.github.smooozy01.exception.general.AlreadyExistsException;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.model.Car;
import io.github.smooozy01.repository.CarRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {
    
    private final CarRepository carRepository;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
    
    public List<CarDTO> getCars(String name){
        
        if (name == null || name.isBlank())
            return CarDTO.getDtoListFromModelList(carRepository.findAll());
        
        else 
            return CarDTO.getDtoListFromModelList(
                    carRepository.findByNameContainingIgnoreCase(name));
    }
    
    
    public CarDTO getCarByID(int id){
        
        return carRepository.findById(id)
                .map(CarDTO::getDtoFromModel)
                .orElseThrow(() -> new DoesntExistException("No record with such ID"));
    }
    
    
    @Transactional
    public ResponseEntity<String> updateCarByID(int id, CarDTO carDTO){
        
        try {
            
            Car car = carRepository.findById(id).orElseThrow(() -> 
                    new DoesntExistException("No record with such ID"));
            
            if (carDTO.getName() != null)
                car.setName(carDTO.getName());
            
            return ResponseEntity.status(HttpStatus.OK).body("Updated");
            
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new DoesntExistException("No record with such ID");
        }
        
    }
    
    public ResponseEntity<String> addCar(CarDTO carDTO){

        if (carDTO.getName() == null || carDTO.getName().replaceAll(" ", "").length() < 5)
            throw new IllegalArgumentException("Car name can't be empty");
        
        try {
            
            carRepository.save(new Car(carDTO.getName().toUpperCase()));
            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
            
        } catch (DataIntegrityViolationException e){
            throw new AlreadyExistsException("Such car already exists");
        }
        
    }
    
    
    public ResponseEntity<String> deleteCarByID(int id){
        
        carRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted");
    }
    
    public boolean doesCarExist(int id){
        return carRepository.existsById(id);
    }
    
    public Optional<Car> getCarModelById(int id){
        return carRepository.findById(id);
    }
        
    
}
