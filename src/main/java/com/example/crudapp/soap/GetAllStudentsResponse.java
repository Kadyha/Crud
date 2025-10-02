package com.example.crudapp.soap;

import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "GetAllStudentsResponse", namespace = "http://example.com/crudapp/soap")
public class GetAllStudentsResponse {
    private List<StudentSoapDTO> students;
    @XmlElement(name = "student")
    public List<StudentSoapDTO> getStudents() { return students; }
    public void setStudents(List<StudentSoapDTO> students) { this.students = students; }
}
