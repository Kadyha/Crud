package com.example.crudapp.soap;

import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "GetAllAddressesResponse", namespace = "http://example.com/crudapp/soap")
public class GetAllAddressesResponse {
    private List<AddressSoapDTO> addresses;
    @XmlElement(name = "address")
    public List<AddressSoapDTO> getAddresses() { return addresses; }
    public void setAddresses(List<AddressSoapDTO> addresses) { this.addresses = addresses; }
}
