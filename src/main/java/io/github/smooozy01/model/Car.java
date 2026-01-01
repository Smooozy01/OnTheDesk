package io.github.smooozy01.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cars", indexes = {
        @Index(name = "idx_name", columnList = "name", unique = true)
})
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name")
    @Size(min = 5, max = 8)
    private String name;
    
    public Car(int id, String name) {this.name = name; this.id = id;}
    public Car(String name){ this.name = name; }
    public Car(){}

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
