package com.example.crudapp.soap;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "GetAllAddressesRequest", namespace = "http://example.com/crudapp/soap")
public class GetAllAddressesRequest {
    // No fields needed for getAll
}
