package com.example.crudapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends Person {
    @Column(unique = true, nullable = true)
    private String studentNumber;
    private Double averageMark;

    // Example method stub
    public boolean isEligibleToEnroll() {
        return averageMark != null && averageMark >= 50.0;
    }

    public String getSeminarsTaken() {
        // Placeholder for actual logic
        return "Seminars list";
    }

    // Getters and setters
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }
    public Double getAverageMark() { return averageMark; }
    public void setAverageMark(Double averageMark) { this.averageMark = averageMark; }
}
