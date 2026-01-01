package io.github.smooozy01.service;

import io.github.smooozy01.dto.CarDTO;
import io.github.smooozy01.exception.general.AlreadyExistsException;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.model.Car;
import io.github.smooozy01.repository.CarRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    
    private final CarRepository carRepository;
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


    public List<CarDTO> getAllCars(){
        
        List<Car> carList = carRepository.findAll();
        return CarDTO.getDtoListFromModelList(carList);
    }
    
    
    public CarDTO getCarByID(int id){
        
        return carRepository.findById(id)
                .map(CarDTO::getDtoFromModel)
                .orElseThrow(() -> new DoesntExistException("No record with such ID"));
    }
    
    
    public List<CarDTO> getCarByName(String userInput){
        return CarDTO.getDtoListFromModelList(
                carRepository.findByNameContainingIgnoreCase(userInput));
    }
    
    
    public void updateCarByID(int id, CarDTO carDTO){
        
        carRepository.save(new Car(id, carDTO.getName()));
    }
    
    
    public void addCar(CarDTO carDTO){

        System.out.println(carDTO.getName());
        if (carDTO.getName() == null || carDTO.getName().replaceAll(" ", "").length() < 5)
            throw new IllegalArgumentException("Car name can't be empty");
        
        try {
            carRepository.save(new Car(carDTO.getName().toUpperCase()));
        } catch (DataIntegrityViolationException e){
            throw new AlreadyExistsException("Such car already exists");
        }
        
    }
    
    
    public void deleteCarByID(int id){
        
        carRepository.deleteById(id);
    }
    
}
