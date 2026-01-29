package io.github.smooozy01.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(min = 3)
    private String name;    
    
    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;

    
    private Boolean active;
    
    public Client() {}
    public Client(String name) { 
        this.name = name; 
        this.car = null;
        this.active = false;
    }
    
    public Client(String name, Car car) {
        this.name = name;
        this.car = car;
    }

    public Client(String name, Car car,  Boolean active) {
        this.name = name;
        this.car = car;
        this.active = active;
    }
    
}
