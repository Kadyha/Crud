package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetAllPersonsRequest", namespace = "http://example.com/crudapp/soap")
public class GetAllPersonsRequest {
    // No fields needed for getAll
}
