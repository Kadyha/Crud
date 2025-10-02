package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "UpdatePersonRequest", namespace = "http://example.com/crudapp/soap")
@XmlType(propOrder = {"id", "person"})
public class UpdatePersonRequest {
    private Long id;
    private PersonSoapDTO person;
    @XmlElement(name = "id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @XmlElement(name = "person")
    public PersonSoapDTO getPerson() { return person; }
    public void setPerson(PersonSoapDTO person) { this.person = person; }
}
