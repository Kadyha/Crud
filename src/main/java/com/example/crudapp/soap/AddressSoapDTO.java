package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "address", namespace = "http://example.com/crudapp/soap")
@XmlType(name = "AddressDto", propOrder = {"id", "street", "city", "state", "postalCode", "country"})
public class AddressSoapDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    @XmlElement
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @XmlElement
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    @XmlElement
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @XmlElement
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    @XmlElement
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    @XmlElement
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
