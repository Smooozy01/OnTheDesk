package io.github.smooozy01.service;

import io.github.smooozy01.dto.RefuelDTO;
import io.github.smooozy01.exception.general.DoesntExistException;
import io.github.smooozy01.exception.general.InvalidDateException;
import io.github.smooozy01.exception.refuel.*;
import io.github.smooozy01.model.RefuelModel;
import io.github.smooozy01.repository.RefuelRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RefuelService {
    
    private final RefuelRepository refuelRepository;
    public RefuelService(RefuelRepository refuelRepository) {
        this.refuelRepository = refuelRepository;
    }
    
    
    @Cacheable(value = "refuels_all")
    public List<RefuelDTO> getAllRefuels(){

        List<RefuelModel> refuelModels = refuelRepository.findAll();
        return RefuelDTO.getDTOListFromModelList(refuelModels);
    }

    
    @Cacheable(value = "refuels", key = "#ID")
    public RefuelDTO getRefuelByID(int ID){
        
        return refuelRepository.findById(ID)
                // 1. If found, convert to DTO and return
                .map(RefuelDTO::getDTOFromModel)
                // 2. If NOT found, throw exception
                .orElseThrow(() -> new DoesntExistException("No records with such id"));
    }

    
    @Caching(
            evict = {@CacheEvict(value = "refuels_all", allEntries = true)}
    )
    public void addRefuel(RefuelDTO refuelDTO) {
        
        Optional<RefuelModel> lastRecord = refuelRepository.findTopByOrderByIdDesc();
        
        if (lastRecord.isEmpty()) {
            refuelRepository.save(RefuelDTO.getModelFromDTO(refuelDTO));
            return;
        }
        RefuelModel lastRefuel = lastRecord.get();
        
        if (refuelDTO.getMileage() <= lastRefuel.getMileage())
            throw new InvalidMileageException("New record's mileage can't be less or equal to the last one");
        
        if (refuelDTO.getDate().isBefore(lastRefuel.getDate()))
            throw new InvalidDateException("New record's date can't be before last record's");
        
        refuelRepository.save(RefuelDTO.getModelFromDTO(refuelDTO));
        
    }
    
}
