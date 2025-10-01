package com.example.crudapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("PROFESSOR")
public class Professor extends Person {
    private Double salary;

    // Getters and setters
    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }
}
