package io.github.smooozy01.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class RefuelModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private LocalDate date;
    private float amount;
    private int price;
    
    @Column(unique = true)
    private int mileage;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        
        RefuelModel that = (RefuelModel) o;
        return Float.compare(amount, that.amount) == 0 && price == that.price && mileage == that.mileage && Objects.equals(date, that.date);
    }

    public RefuelModel() {
        
    }
    
    public RefuelModel(LocalDate date, float amount, int price, int mileage) {
        this.date = date;
        this.amount = amount;
        this.price = price;
        this.mileage = mileage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }
    
}
