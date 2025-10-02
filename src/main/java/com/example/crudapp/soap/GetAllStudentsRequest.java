package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetAllStudentsRequest", namespace = "http://example.com/crudapp/soap")
public class GetAllStudentsRequest {
    // No fields needed for getAll
}
