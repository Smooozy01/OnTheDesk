package io.github.smooozy01.controller;
import io.github.smooozy01.dto.RefuelDTO;
import io.github.smooozy01.service.RefuelService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("refuel")
public class RefuelController {

    private final RefuelService refuelService;
    public RefuelController(RefuelService refuelService) { this.refuelService = refuelService; }

    
    @GetMapping
    public List<RefuelDTO> getAllRefuels(){ 
        
        return refuelService.getAllRefuels(); 
    }
    
    
    @PostMapping("addrefuel")
    public ResponseEntity<String> addRefuel(@RequestBody @Valid RefuelDTO refuelDTO){

        refuelService.addRefuel(refuelDTO);
        return ResponseEntity.ok("Saved");
    }
    
    
    @GetMapping("{id}")
    public RefuelDTO getRefuelByID(@PathVariable int id){
        
        return refuelService.getRefuelByID(id);
    }
    
//    @GetMapping
//    public List<RefuelModel> getRefuels(@Request  Param(required = false) Integer id){
//        
//        if (id == null)
//            return refuelService.getAllRefuels();
//        
//        else {
//            Optional<RefuelModel> refuelObject = refuelService.getRefuelByID(id);
//            if (refuelObject.isPresent())
//                return List.of(refuelObject.get());
//
//            return null;
//        }
//    }
}
