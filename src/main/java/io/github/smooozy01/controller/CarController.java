package io.github.smooozy01.controller;

import io.github.smooozy01.dto.CarDTO;
import io.github.smooozy01.service.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {
    
    private final CarService carService;
    public CarController(CarService carService) { this.carService = carService; }
    
    
    @GetMapping
    public List<CarDTO> getCars(@RequestParam(value = "name", required = false) String name){
        
        if (name == null)
            return carService.getAllCars();
        else
            return carService.getCarByName(name);
    }
    
    @GetMapping("{id}")
    public CarDTO getCarByID(@PathVariable int id){
        return carService.getCarByID(id);
    }
    
    
    @PutMapping("{id}")
    public ResponseEntity<String> updateCarByID(@PathVariable int id, 
                                                @RequestBody CarDTO carDTO){
        
        carService.updateCarByID(id, carDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }
    
    @PostMapping()
    public ResponseEntity<String> addCar(@RequestBody CarDTO carDTO){
        
        carService.addCar(carDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCarByID(@PathVariable int id){
        
        carService.deleteCarByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Deleted")      ;  
    }
    
}
