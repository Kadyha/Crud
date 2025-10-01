package com.example.crudapp.soap;

import com.example.crudapp.model.Address;
import java.util.List;

public class GetAllAddressesResponse {
    private List<Address> addresses;
    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
}
