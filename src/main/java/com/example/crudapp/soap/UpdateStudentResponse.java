package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "UpdateStudentResponse", namespace = "http://example.com/crudapp/soap")
public class UpdateStudentResponse {
    private StudentSoapDTO student;
    @XmlElement(name = "student")
    public StudentSoapDTO getStudent() { return student; }
    public void setStudent(StudentSoapDTO student) { this.student = student; }
}
