package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "UpdateProfessorRequest", namespace = "http://example.com/crudapp/soap")
@XmlType(propOrder = {"id", "professor"})
public class UpdateProfessorRequest {
    private Long id;
    private ProfessorSoapDTO professor;
    @XmlElement(name = "id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @XmlElement(name = "professor")
    public ProfessorSoapDTO getProfessor() { return professor; }
    public void setProfessor(ProfessorSoapDTO professor) { this.professor = professor; }
}
