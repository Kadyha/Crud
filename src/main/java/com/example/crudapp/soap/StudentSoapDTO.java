package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "student", namespace = "http://example.com/crudapp/soap")
@XmlType(name = "StudentDto", propOrder = {"id", "firstName", "lastName", "email", "phone", "studentNumber", "averageMark", "address"})
public class StudentSoapDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String studentNumber;
    private Double averageMark;
    private AddressSoapDTO address;

    @XmlElement public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @XmlElement public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    @XmlElement public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    @XmlElement public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @XmlElement public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @XmlElement public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

    @XmlElement public Double getAverageMark() { return averageMark; }
    public void setAverageMark(Double averageMark) { this.averageMark = averageMark; }

    @XmlElement public AddressSoapDTO getAddress() { return address; }
    public void setAddress(AddressSoapDTO address) { this.address = address; }
}
