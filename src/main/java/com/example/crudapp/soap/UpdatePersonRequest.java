package com.example.crudapp.soap;


public class UpdatePersonRequest {
    private Long id;
    private PersonSoapDTO person;
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PersonSoapDTO getPerson() { return person; }
    public void setPerson(PersonSoapDTO person) { this.person = person; }
}
