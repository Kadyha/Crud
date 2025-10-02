package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetPersonByIdResponse", namespace = "http://example.com/crudapp/soap")
public class GetPersonByIdResponse {
    private PersonSoapDTO person;
    @XmlElement(name = "person")
    public PersonSoapDTO getPerson() { return person; }
    public void setPerson(PersonSoapDTO person) { this.person = person; }
}
