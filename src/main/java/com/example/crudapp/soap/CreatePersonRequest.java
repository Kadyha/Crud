package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "CreatePersonRequest", namespace = "http://example.com/crudapp/soap")
@jakarta.xml.bind.annotation.XmlType(propOrder = {"person"})
public class CreatePersonRequest {
    private PersonSoapDTO person;

    @XmlElement(name = "person", required = true)
    public PersonSoapDTO getPerson() { return person; }
    public void setPerson(PersonSoapDTO person) { this.person = person; }
}
