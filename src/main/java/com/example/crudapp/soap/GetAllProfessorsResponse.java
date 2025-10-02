package com.example.crudapp.soap;

import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "GetAllProfessorsResponse", namespace = "http://example.com/crudapp/soap")
public class GetAllProfessorsResponse {
    private List<ProfessorSoapDTO> professors;
    @XmlElement(name = "professor")
    public List<ProfessorSoapDTO> getProfessors() { return professors; }
    public void setProfessors(List<ProfessorSoapDTO> professors) { this.professors = professors; }
}
