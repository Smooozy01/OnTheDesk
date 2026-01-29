package io.github.smooozy01.dto;

import io.github.smooozy01.model.Car;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CarDTO implements Serializable {
    
    private Integer id;
    
    @Size(min = 5, max = 8)
    private String name;
    
    
    public static Car getModelFromDto(CarDTO carDTO){
        return new Car(carDTO.getName());
    }
    
    public static CarDTO getDtoFromModel(Car car){
        return new CarDTO(car.getName(), car.getId());
    }

    public static List<Car> getModelListFromDtoList(List<CarDTO> carDTOList){
        
        List<Car> carModelList = new ArrayList<>(carDTOList.size());
        
        for (CarDTO carDTO : carDTOList){
            carModelList.add(getModelFromDto(carDTO));
        }
        
        return carModelList;
    }

    public static List<CarDTO> getDtoListFromModelList(List<Car> carModelList){

        List<CarDTO> carDTOList = new ArrayList<>(carModelList.size());

        for (Car carModel : carModelList){
            carDTOList.add(getDtoFromModel(carModel));
        }

        return carDTOList;
    }
    
    
    public CarDTO(){}
    public CarDTO(String name){ this.name = name;}
    public CarDTO(String name, Integer id){ this.name = name; this.id = id; }
    
}
