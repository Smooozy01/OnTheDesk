package io.github.smooozy01.dto;

import io.github.smooozy01.model.RefuelModel;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RefuelDTO {

    @NotNull
    @PastOrPresent(message = "Can't create a record from future")
    private final LocalDate date;
    
    @Positive(message = "Fuel amount can't be zero or less")
    private final float amount;

    @Positive(message = "Price amount can't be zero or less")
    private final int price;

    @Positive(message = "Mileage amount can't be zero or less")
    private final int mileage;

    public RefuelDTO(LocalDate date, float amount, int price, int mileage) {
        this.date = date;
        this.amount = amount;
        this.price = price;
        this.mileage = mileage;
    }
    
    public static RefuelDTO getDTOFromModel(RefuelModel model){
        
        return new RefuelDTO(model.getDate(), model.getAmount(), model.getPrice(), model.getMileage());
    }
    
    public static List<RefuelDTO> getDTOListFromModelList(List<RefuelModel> refuelModels){

        List<RefuelDTO> refuelDTOS = new ArrayList<>();
        for (RefuelModel model : refuelModels){
            refuelDTOS.add(RefuelDTO.getDTOFromModel(model));
        }

        return refuelDTOS;
    }
    
    public static RefuelModel getModelFromDTO(RefuelDTO refuelDTO){
        
        return new RefuelModel(refuelDTO.getDate(), refuelDTO.getAmount(), refuelDTO.getPrice(), refuelDTO.getMileage());
    }
    

    public LocalDate getDate() {
        return date;
    }

    public float getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public int getMileage() {
        return mileage;
    }
    
}
