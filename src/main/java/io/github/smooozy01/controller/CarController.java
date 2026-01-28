package io.github.smooozy01.controller;
import io.github.smooozy01.dto.CarDTO;
import io.github.smooozy01.facade.ClientCarFacade;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cars")
public class CarController {
    
    private final ClientCarFacade facade;
    public CarController(ClientCarFacade facade) { this.facade = facade; }
    
    
    @GetMapping
    public List<CarDTO> getCars(@RequestParam(value = "name", required = false) String name){
        
        return facade.getCars(name);
    }
    
    @GetMapping("{id}")
    public CarDTO getCarByID(@PathVariable int id){
        
        return facade.getCarByID(id);
    }
    
    @PostMapping()
    public ResponseEntity<String> addCar(@RequestBody @Valid CarDTO carDTO){
        
        return facade.addCar(carDTO);
    }
    
    @PutMapping("{id}")
    public ResponseEntity<String> updateCarByID(@PathVariable int id, 
                                                @RequestBody @Valid CarDTO carDTO){
        
        return facade.updateCarByID(id, carDTO);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCarByID(@PathVariable int id){
        
        return facade.deleteCarByID(id);  
    }
    
}
