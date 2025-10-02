package com.example.crudapp.soap;

import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "GetAllPersonsResponse", namespace = "http://example.com/crudapp/soap")
public class GetAllPersonsResponse {
    private List<PersonSoapDTO> persons;
    @XmlElement(name = "person")
    public List<PersonSoapDTO> getPersons() { return persons; }
    public void setPersons(List<PersonSoapDTO> persons) { this.persons = persons; }
}
