package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateProfessorRequest", namespace = "http://example.com/crudapp/soap")
public class CreateProfessorRequest {
    private ProfessorSoapDTO professor;
    @XmlElement(name = "professor", required = true)
    public ProfessorSoapDTO getProfessor() { return professor; }
    public void setProfessor(ProfessorSoapDTO professor) { this.professor = professor; }
}
