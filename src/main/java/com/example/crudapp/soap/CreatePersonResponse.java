package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "CreatePersonResponse", namespace = "http://example.com/crudapp/soap")
public class CreatePersonResponse {
    private PersonSoapDTO person;
    @XmlElement(name = "person")
    public PersonSoapDTO getPerson() { return person; }
    public void setPerson(PersonSoapDTO person) { this.person = person; }
}
