package com.example.crudapp.soap;

import com.example.crudapp.model.Person;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "GetAllPersonsResponse", namespace = "http://example.com/crudapp/soap")
public class GetAllPersonsResponse {
    private List<Person> persons;
    @XmlElement(name = "person")
    public List<Person> getPersons() { return persons; }
    public void setPersons(List<Person> persons) { this.persons = persons; }
}
