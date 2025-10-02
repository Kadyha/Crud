package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "UpdateStudentRequest", namespace = "http://example.com/crudapp/soap")
@XmlType(propOrder = {"id", "student"})
public class UpdateStudentRequest {
    private Long id;
    private StudentSoapDTO student;
    @XmlElement(name = "id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @XmlElement(name = "student")
    public StudentSoapDTO getStudent() { return student; }
    public void setStudent(StudentSoapDTO student) { this.student = student; }
}
