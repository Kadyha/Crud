package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetProfessorByIdResponse", namespace = "http://example.com/crudapp/soap")
public class GetProfessorByIdResponse {
    private ProfessorSoapDTO professor;
    @XmlElement(name = "professor")
    public ProfessorSoapDTO getProfessor() { return professor; }
    public void setProfessor(ProfessorSoapDTO professor) { this.professor = professor; }
}
