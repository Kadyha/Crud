package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UpdatePersonResponse", namespace = "http://example.com/crudapp/soap")
public class UpdatePersonResponse {
    private PersonSoapDTO person;
    @XmlElement(name = "person")
    public PersonSoapDTO getPerson() { return person; }
    public void setPerson(PersonSoapDTO person) { this.person = person; }
}
