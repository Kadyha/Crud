package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CreateStudentRequest", namespace = "http://example.com/crudapp/soap")
public class CreateStudentRequest {
    private StudentSoapDTO student;
    @XmlElement(name = "student", required = true)
    public StudentSoapDTO getStudent() { return student; }
    public void setStudent(StudentSoapDTO student) { this.student = student; }
}
