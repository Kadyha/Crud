package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateProfessorResponse", namespace = "http://example.com/crudapp/soap")
public class CreateProfessorResponse {
    private ProfessorSoapDTO professor;
    @XmlElement(name = "professor")
    public ProfessorSoapDTO getProfessor() { return professor; }
    public void setProfessor(ProfessorSoapDTO professor) { this.professor = professor; }
}
