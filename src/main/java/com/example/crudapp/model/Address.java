package com.example.crudapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
// JAXB annotations removed; use SOAP DTOs for XML mapping
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String postalCode;
    @NotBlank
    private String country;

    // Validate address fields
    public boolean validate() {
        return street != null && !street.isBlank()
            && city != null && !city.isBlank()
            && state != null && !state.isBlank()
            && postalCode != null && !postalCode.isBlank()
            && country != null && !country.isBlank();
    }

    // Output address as label
    public String outputAsLabel() {
        return String.format("%s, %s, %s, %s, %s", street, city, state, postalCode, country);
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}

