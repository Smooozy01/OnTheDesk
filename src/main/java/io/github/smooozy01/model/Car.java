package io.github.smooozy01.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cars", indexes = {
        @Index(name = "idx_name", columnList = "name", unique = true)
})

@Getter @Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "name", unique = true)
    @Size(min = 5, max = 8)
    private String name;
    
    @OneToMany(mappedBy = "car")
    private List<Client> clients;
    
    public Car(int id, String name) {this.name = name; this.id = id;}
    public Car(String name){ this.name = name; }
    public Car(){}
    
}
