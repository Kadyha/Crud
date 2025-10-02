package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "GetStudentByIdRequest", namespace = "http://example.com/crudapp/soap")
@XmlType(propOrder = {"id"})
public class GetStudentByIdRequest {
    private Long id;
    @XmlElement(name = "id")
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
