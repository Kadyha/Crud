package com.example.crudapp.soap;

import com.example.crudapp.model.Person;
import com.example.crudapp.model.Address;
import com.example.crudapp.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PersonSoapEndpoint {
    private static final String NAMESPACE_URI = "http://example.com/crudapp/soap";

    @Autowired
    private PersonService personService;

    // Get all persons
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllPersonsRequest")
    @ResponsePayload
    public GetAllPersonsResponse getAllPersons(@RequestPayload GetAllPersonsRequest request) {
        GetAllPersonsResponse response = new GetAllPersonsResponse();
        response.setPersons(personService.getAllPersons());
        return response;
    }

    // Create person
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreatePersonRequest")
    @ResponsePayload
    public CreatePersonResponse createPerson(@RequestPayload CreatePersonRequest request) {
        CreatePersonResponse response = new CreatePersonResponse();
        // Map SOAP DTO to JPA entity
        Person person = mapToEntity(request.getPerson());
        Person created = personService.createPerson(person);
        response.setPerson(mapToSoapDTO(created));
        return response;
    }

    // Update person
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdatePersonRequest")
    @ResponsePayload
    public UpdatePersonResponse updatePerson(@RequestPayload UpdatePersonRequest request) {
        UpdatePersonResponse response = new UpdatePersonResponse();
        Person person = mapToEntity(request.getPerson());
        Person updated = personService.updatePerson(request.getId(), person);
        response.setPerson(mapToSoapDTO(updated));
        return response;
    }
    // Helper methods to map between SOAP DTO and JPA entity
    private Person mapToEntity(PersonSoapDTO dto) {
        if (dto == null) return null;
        Person entity = new Person();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        if (dto.getAddress() != null) {
            Address addr = new Address();
            addr.setId(dto.getAddress().getId());
            addr.setStreet(dto.getAddress().getStreet());
            addr.setCity(dto.getAddress().getCity());
            addr.setState(dto.getAddress().getState());
            addr.setPostalCode(dto.getAddress().getPostalCode());
            addr.setCountry(dto.getAddress().getCountry());
            entity.setAddress(addr);
        }
        return entity;
    }

    private PersonSoapDTO mapToSoapDTO(Person entity) {
        if (entity == null) return null;
        PersonSoapDTO dto = new PersonSoapDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        if (entity.getAddress() != null) {
            AddressSoapDTO addrDto = new AddressSoapDTO();
            addrDto.setId(entity.getAddress().getId());
            addrDto.setStreet(entity.getAddress().getStreet());
            addrDto.setCity(entity.getAddress().getCity());
            addrDto.setState(entity.getAddress().getState());
            addrDto.setPostalCode(entity.getAddress().getPostalCode());
            addrDto.setCountry(entity.getAddress().getCountry());
            dto.setAddress(addrDto);
        }
        return dto;
    }

    // Delete person
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeletePersonRequest")
    @ResponsePayload
    public DeletePersonResponse deletePerson(@RequestPayload DeletePersonRequest request) {
        DeletePersonResponse response = new DeletePersonResponse();
        boolean deleted = personService.deletePerson(request.getId());
        response.setSuccess(deleted);
        return response;
    }
}
